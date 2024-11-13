-- Disable foreign key constraints
PRAGMA foreign_keys = OFF;

-- Begin transaction
BEGIN TRANSACTION;

-- Generate DROP TABLE statements for all tables
SELECT 'DROP TABLE IF EXISTS ' || name || ';'
FROM sqlite_master
WHERE type = 'table';

-- Execute the generated DROP TABLE statements
-- (You will need to copy the output of the above SELECT statement and execute it)

-- Commit transaction
COMMIT;

-- Enable foreign key constraints
PRAGMA foreign_keys = ON;