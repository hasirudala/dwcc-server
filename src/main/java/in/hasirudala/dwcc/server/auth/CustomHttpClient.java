package in.hasirudala.dwcc.server.auth;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.net.ProxySelector;

/**
 * Apache HTTP client implementation with:
 * - custom short Keep Alive policy
 * - supports 'retry once' policy
 * - has low number of connections
 * - has stale checking enabled
 */
public class CustomHttpClient {
    public static HttpClient newHttpClient() {
        return newDefaultHttpClient(
                SSLSocketFactory.getSocketFactory(), newDefaultHttpParams(), ProxySelector.getDefault());
    }

    /** Returns a new instance of the default HTTP parameters we use. */
    static HttpParams newDefaultHttpParams() {
        HttpParams params = new BasicHttpParams();
        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, true);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        ConnManagerParams.setMaxTotalConnections(params, 10);
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(5));
        return params;
    }

    /**
     *
     * @param socketFactory SSL socket factory
     * @param params HTTP parameters
     * @param proxySelector HTTP proxy selector to use {@link ProxySelectorRoutePlanner} or
     *        {@code null} for {@link DefaultHttpRoutePlanner}
     * @return new instance of the Apache HTTP client
     */
    static DefaultHttpClient newDefaultHttpClient(
            SSLSocketFactory socketFactory, HttpParams params, ProxySelector proxySelector) {
        // See http://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", socketFactory, 443));
        ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(params, registry);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(connectionManager, params);
        defaultHttpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(1, true));
        if (proxySelector != null) {
            defaultHttpClient.setRoutePlanner(new ProxySelectorRoutePlanner(registry, proxySelector));
        }
        defaultHttpClient.setKeepAliveStrategy(new ShortKeepAliveStrategy());
        return defaultHttpClient;
    }

    public static class ShortKeepAliveStrategy implements ConnectionKeepAliveStrategy {

        @Override
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            // Keep alive for 5 seconds only
            return 5 * 1000;
        }
    }
}
