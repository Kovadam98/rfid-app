/*
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

*/
INSERT INTO product_type(id,name) VALUES
(1,'Motor Bike');

INSERT INTO component_type(id,name,assembly_order,image_url,product_type_id) VALUES
(1,'Exhaust Pipe',1,'images/exhaust-pipe.png',1),
(2,'Tyre Back Upper',2,'images/tyre-back-upper.png',1),
(3,'Tyre Back Lower',3,'images/tyre-back-lower.png',1),
(4,'Chassis',4,'images/chassis.png',1),
(5,'Fuel Tank',5,'images/fuel-tank.png',1),
(6,'Saddle',6,'images/saddle.png',1),
(7,'Steering Stem',7,'images/steering-stem.png',1),
(8,'Tyre Front',8,'images/tyre-front.png',1);

INSERT INTO color_type(id,name,hue,saturation,brightness) VALUES
(1,'gray',0,0,250),
(2,'black',0,100,0),
(3,'red',0,100,130),
(4,'green',120,100,130),
(5,'blue',240,100,130);

INSERT INTO color_type_join(component_type_id, color_type_id) VALUES
(1,1),(1,2),
(2,2),
(3,2),
(4,3),(4,4),(4,5),
(5,3),(5,4),(5,5),
(6,2),
(7,1),(7,2),
(8,2);

INSERT INTO component(id ,is_real ,component_type_id, color_type_id, product_id) VALUES
(1,true,1,1,null),
(2,true,1,2,null),
(3,true,2,2,null),
(4,true,2,2,null),
(5,true,3,2,null),
(6,true,3,2,null),
(7,true,4,3,null),
(8,true,4,4,null),
(9,true,4,5,null),
(10,true,5,3,null),
(11,true,5,4,null),
(12,true,5,5,null),
(13,true,6,2,null),
(14,true,7,1,null),
(15,true,7,2,null),
(16,true,8,2,null);
