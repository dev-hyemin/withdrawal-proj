USE finance;

-- ========================
-- balance table
-- ========================
CREATE TABLE IF NOT EXISTS balance (
    wallet_id VARCHAR(36) NOT NULL PRIMARY KEY,
    balance DECIMAL(18, 6) NOT NULL DEFAULT 0,
    last_update_dtime DATETIME NULL
);

-- ========================
-- balance temp data
-- ========================
INSERT INTO balance (wallet_id, balance, last_update_dtime)
VALUES
    ('2a79cd80-c768-11f0-a863-bef5164605b6', 10000.000000, NOW()),
    ('5b32769f-c768-11f0-a863-bef5164605b6', 3500.500000, NOW()),
    ('60cb980e-c768-11f0-a863-bef5164605b6', 0.000000, NOW());

-- ========================
-- transaction_history table
-- ========================
CREATE TABLE IF NOT EXISTS transaction_history (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    wallet_id VARCHAR(36) NOT NULL,
    amount DECIMAL(18, 6) NOT NULL,
    balance DECIMAL(18, 6) NOT NULL,
    reg_dtime DATETIME NOT NULL,
    INDEX idx_wallet_id (wallet_id)
);

-- ========================
-- transaction_history temp data
-- ========================
INSERT INTO transaction_history (wallet_id, amount, balance, reg_dtime)
VALUES
    ('2a79cd80-c768-11f0-a863-bef5164605b6', 1000.000000, 11000.000000, NOW()),
    ('2a79cd80-c768-11f0-a863-bef5164605b6', -500.000000, 10500.000000, NOW()),
    ('5b32769f-c768-11f0-a863-bef5164605b6', 3500.500000, 7000.500000, NOW());
