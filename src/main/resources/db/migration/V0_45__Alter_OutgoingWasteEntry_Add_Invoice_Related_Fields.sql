ALTER TABLE outgoing_waste_entries
  ADD COLUMN vehicle_number TEXT,
  ADD COLUMN weighbridge_slip_number TEXT,
  ADD COLUMN invoice_number TEXT,
  ADD COLUMN invoice_date DATE;
