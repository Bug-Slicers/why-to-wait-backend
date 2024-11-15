-- Enum Types
CREATE TYPE "user_role" AS ENUM (
  'CUSTOMER',
  'SUPER_ADMIN'
);

CREATE TYPE "merchant_role" AS ENUM (
  'MERCHANT_ADMIN',
  'MERCHANT_OPERATOR',
  'MERCHANT_OWNER'
);

CREATE TYPE "day_of_week" AS ENUM (
  'MONDAY',
  'TUESDAY',
  'WEDNESDAY',
  'THURSDAY',
  'FRIDAY',
  'SATURDAY',
  'SUNDAY'
);

CREATE TYPE "merchant_type" AS ENUM (
  'DINE_IN',
  'TAKE_AWAY'
);

CREATE TYPE "item_tag" AS ENUM (
  'VEG',
  'NONVEG',
  'JAIN'
);

CREATE TYPE "order_status" AS ENUM (
  'ACCEPTED',
  'PREPARING',
  'COMPLETED'
);

CREATE TYPE "order_item_status" AS ENUM (
  'PREPARING',
  'SERVED'
);

-- Tables
CREATE TABLE "users" (
  "id" uuid PRIMARY KEY,
  "first_name" varchar NOT NULL,
  "last_name" varchar,
  "email" varchar UNIQUE NOT NULL,
  "email_verified" bool DEFAULT false,
  "mobile" varchar UNIQUE NOT NULL,
  "mobile_verified" bool DEFAULT false,
  "google_id" varchar UNIQUE,
  "role" "user_role",
  "last_logout" timestamp,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "hashed_password" (
  "id" uuid,
  "user_id" uuid,
  "hashed_password" varchar
);

CREATE TABLE "merchant" (
  "id" uuid PRIMARY KEY,
  "restaurant_name" varchar,
  "address" uuid NOT NULL,
  "owner" uuid NOT NULL,
  "type" "merchant_type" NOT NULL DEFAULT 'DINE_IN',
  "is_online" bool DEFAULT false,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "merchant_managers" (
  "id" uuid PRIMARY KEY,
  "user_id" uuid NOT NULL,
  "merchant_id" uuid NOT NULL,
  "role" "merchant_role"
);

CREATE TABLE "timing" (
  "id" uuid PRIMARY KEY,
  "merchant_id" uuid NOT NULL,
  "day_of_week" "day_of_week" NOT NULL,
  "open_time" time,
  "close_time" time,
  "is_closed" bool DEFAULT false,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "address" (
  "id" uuid PRIMARY KEY,
  "address_line1" varchar NOT NULL,
  "address_line2" varchar,
  "city" varchar,
  "district" varchar,
  "state" varchar,
  "pincode" varchar(6),
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "menu_category" (
  "id" uuid PRIMARY KEY,
  "name" varchar NOT NULL,
  "merchant_id" uuid NOT NULL,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "menu_item" (
  "id" uuid PRIMARY KEY,
  "merchant_id" uuid NOT NULL,
  "name" varchar NOT NULL,
  "description" varchar,
  "category" uuid NOT NULL,
  "tag" "item_tag" NOT NULL,
  "price" float NOT NULL,
  "image_url" varchar,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "merchant_table" (
  "id" uuid PRIMARY KEY,
  "merchant_id" uuid NOT NULL,
  "table_number" int NOT NULL,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "qr_code" (
  "id" uuid PRIMARY KEY,
  "table_id" uuid,
  "merchant_id" uuid NOT NULL,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "order" (
  "id" uuid PRIMARY KEY,
  "token" integer,
  "merchant_id" uuid NOT NULL,
  "table_id" uuid,
  "status" "order_status" DEFAULT 'ACCEPTED',
  "total_amount" float,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "order_item" (
  "id" uuid PRIMARY KEY,
  "item_id" uuid NOT NULL,
  "order_id" uuid NOT NULL,
  "instruction" varchar,
  "quantity" int,
  "amount" float,
  "status" "order_item_status" DEFAULT 'PREPARING',
  "created_at" timestamp
);

-- Indexes
CREATE UNIQUE INDEX ON "timing" ("merchant_id", "day_of_week");

CREATE UNIQUE INDEX ON "merchant_table" ("merchant_id", "table_number");

-- Foreign Keys
ALTER TABLE "hashed_password" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "merchant" ADD FOREIGN KEY ("address") REFERENCES "address" ("id");

ALTER TABLE "merchant" ADD FOREIGN KEY ("owner") REFERENCES "users" ("id");

ALTER TABLE "merchant_managers" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "merchant_managers" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");

ALTER TABLE "timing" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");

ALTER TABLE "menu_category" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");

ALTER TABLE "menu_item" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");

ALTER TABLE "menu_item" ADD FOREIGN KEY ("category") REFERENCES "menu_category" ("id");

ALTER TABLE "merchant_table" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");

ALTER TABLE "qr_code" ADD FOREIGN KEY ("table_id") REFERENCES "merchant_table" ("id");

ALTER TABLE "qr_code" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");

ALTER TABLE "order" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");

ALTER TABLE "order" ADD FOREIGN KEY ("table_id") REFERENCES "merchant_table" ("id");

ALTER TABLE "order_item" ADD FOREIGN KEY ("item_id") REFERENCES "menu_item" ("id");

ALTER TABLE "order_item" ADD FOREIGN KEY ("order_id") REFERENCES "order" ("id");
