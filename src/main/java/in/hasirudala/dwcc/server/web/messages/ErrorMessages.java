package in.hasirudala.dwcc.server.web.messages;


public class ErrorMessages {
    public static String getRecordNotFoundMsg(String entityName, Long id) {
        return String.format("%s with id %d doesn't exist", entityName, id);
    }
}
