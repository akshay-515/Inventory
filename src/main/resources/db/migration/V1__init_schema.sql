-- 1. CUSTOM ENUM TYPES
CREATE TYPE public.user_role AS ENUM (
    'SUPER_ADMIN',
    'WAREHOUSE_MANAGER',
    'SHOP_STAFF',
    'SELLER',
    'CUSTOMER'
);

CREATE TYPE public.shipment_type AS ENUM (
    'VENDOR_TO_WH',
    'WH_TO_SHOP'
);

CREATE TYPE public.shipment_status AS ENUM (
    'ORDERED_FROM_VENDOR',
    'INTRANSIT_TO_WH',
    'ARRIVED_AT_WH',
    'REQUESTED_BY_SHOP',
    'DISPATCHED_TO_SHOP',
    'DELIVERED'
);

-- 2. TABLES & CONSTRAINTS
CREATE TABLE public.users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role public.user_role NOT NULL,
    is_active BOOLEAN DEFAULT true NOT NULL,
    CONSTRAINT users_username_key UNIQUE (username)
);

CREATE TABLE public.products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    sku VARCHAR(50) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    image_url VARCHAR(512) NOT NULL,
    low_stock_threshold INTEGER DEFAULT 10 NOT NULL,
    is_active BOOLEAN DEFAULT true NOT NULL,
    CONSTRAINT products_sku_key UNIQUE (sku),
    CONSTRAINT chk_positive_price CHECK (price >= 0),
    CONSTRAINT chk_positive_threshold CHECK (low_stock_threshold >= 0)
);

CREATE TABLE public.shop_inventories (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    stock_count INTEGER DEFAULT 0 NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT shop_inventories_product_id_key UNIQUE (product_id),
    CONSTRAINT chk_positive_shop_stock CHECK (stock_count >= 0)
);

CREATE TABLE public.warehouse_inventories (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    stock_count INTEGER DEFAULT 0 NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT warehouse_inventories_product_id_key UNIQUE (product_id),
    CONSTRAINT chk_positive_wh_stock CHECK (stock_count >= 0)
);

CREATE TABLE public.shipments (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    type public.shipment_type NOT NULL,
    status public.shipment_status NOT NULL,
    created_by_id BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_positive_quantity CHECK (quantity > 0)
);

-- 3. FOREIGN KEYS
ALTER TABLE ONLY public.shop_inventories
    ADD CONSTRAINT fk_shop_prod FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE RESTRICT;

ALTER TABLE ONLY public.warehouse_inventories
    ADD CONSTRAINT fk_wh_prod FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE RESTRICT;

ALTER TABLE ONLY public.shipments
    ADD CONSTRAINT fk_shipment_prod FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE RESTRICT;

ALTER TABLE ONLY public.shipments
    ADD CONSTRAINT fk_shipment_user FOREIGN KEY (created_by_id) REFERENCES public.users(id) ON DELETE RESTRICT;