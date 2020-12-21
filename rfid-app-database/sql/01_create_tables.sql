CREATE TABLE product_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE component_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    assembly_order INTEGER NOT NULL,
    image_url VARCHAR(200) NOT NULL,
    product_type_id INTEGER NOT NULL REFERENCES product_type
);

CREATE TABLE color_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    hue INTEGER NOT NULL,
    saturation INTEGER NOT NULL,
    brightness INTEGER NOT NULL
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    product_type_id INTEGER NOT NULL REFERENCES product_type
);

CREATE TABLE component (
    id SERIAL PRIMARY KEY,
    is_real BOOLEAN NOT NULL,
    component_type_id INTEGER NOT NULL REFERENCES component_type,
    color_type_id INTEGER NOT NULL REFERENCES color_type,
    product_id INTEGER REFERENCES product
);

CREATE TABLE color_type_join (
    component_type_id INTEGER NOT NULL REFERENCES component_type,
    color_type_id INTEGER NOT NULL REFERENCES color_type,
    CONSTRAINT pk_color_type_join PRIMARY KEY(color_type_id,component_type_id)
);

CREATE TABLE subscription (
    end_point VARCHAR(300),
    public_key VARCHAR(100),
    auth VARCHAR(100)
);
