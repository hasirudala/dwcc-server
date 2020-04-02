ALTER TABLE audit.outgoing_waste_entries_aud
  ADD COLUMN vehicle_number TEXT,
  ADD COLUMN weighbridge_slip_number TEXT,
  ADD COLUMN invoice_number TEXT,
  ADD COLUMN invoice_date DATE;
