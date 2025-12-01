CREATE TABLE IF NOT EXISTS PRICES (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    price_list_id   BIGINT NOT NULL,
    brand_id        BIGINT NOT NULL,
    start_date      TIMESTAMP NOT NULL,
    end_date        TIMESTAMP NOT NULL,
    product_id      BIGINT NOT NULL,
    priority        INTEGER NOT NULL,
    price           DOUBLE NOT NULL,
    curr            VARCHAR(3) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_prices_product_brand
    ON PRICES(product_id, brand_id);

CREATE INDEX IF NOT EXISTS idx_prices_start_end
    ON PRICES(start_date, end_date);

CREATE INDEX IF NOT EXISTS idx_prices_product_brand_dates
    ON PRICES(product_id, brand_id, start_date, end_date);
