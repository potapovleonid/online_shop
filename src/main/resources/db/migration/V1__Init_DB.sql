create table bucket_products
    (bucket_id int8 not null,
    product_id int8 not null);

create table buckets_tbl
    (bucket_id int8 not null,
    user_id int8,
    primary key (bucket_id));

create table categories_tbl
    (category_id int8 not null,
    title_fld varchar(255),
    primary key (category_id));

create table order_details_tbl
    (order_detail_id int8 not null,
    amount_fld numeric(19, 2),
    price_fld numeric(19, 2),
    order_id int8,
    product_id int8,
    primary key (order_detail_id));

create table orders_tbl
    (order_id int8 not null,
    address_fld varchar(255),
    changed_fld timestamp,
    created_fld timestamp,
    status_fld varchar(255),
    summ_fld numeric(19, 2),
    user_user_id int8,
    primary key (order_id));

create table products_categories
    (product_id int8 not null,
    category_id int8 not null);

create table products_tbl
    (product_id int8 not null,
    price_fld float8,
    title_fld varchar(255),
    primary key (product_id));

create table users_tbl
    (user_id int8 not null,
    archive_fld boolean,
    email_fld varchar(255),
    name_fld varchar(255),
    password_fld varchar(255),
    role_fld varchar(255),
    bucket_bucket_id int8,
    primary key (user_id));

create sequence bucket_seq start 1 increment 1;
create sequence category_seq start 1 increment 1;
create sequence order_seq start 1 increment 1;
create sequence order_details_seq start 1 increment 1;
create sequence product_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;

alter table if exists bucket_products add constraint fk_product_id foreign key (product_id) references products_tbl;
alter table if exists bucket_products add constraint fk_bucket_id foreign key (bucket_id) references buckets_tbl;
alter table if exists buckets_tbl add constraint fk_user_id foreign key (user_id) references users_tbl;
alter table if exists order_details_tbl add constraint fk_order_id foreign key (order_id) references orders_tbl;
alter table if exists order_details_tbl add constraint fk_product_id foreign key (product_id) references products_tbl;
alter table if exists orders_tbl add constraint fk_user_id foreign key (user_user_id) references users_tbl;
alter table if exists products_categories add constraint fk_category_id foreign key (category_id) references categories_tbl;
alter table if exists products_categories add constraint fk_product_id foreign key (product_id) references products_tbl;
alter table if exists users_tbl add constraint fk_bucket_id foreign key (bucket_bucket_id) references buckets_tbl;