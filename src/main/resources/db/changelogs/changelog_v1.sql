--liquibase formatted sql

--my comment: Changeset: Create ENUM Types
--my comment: ================================================

--changeset rohit.patil:001
CREATE TYPE "user_role" AS ENUM (
  'CUSTOMER',
  'SUPER_ADMIN'
);
--rollback DROP TYPE "user_role";

--changeset rohit.patil:002
CREATE TYPE "merchant_role" AS ENUM (
  'MERCHANT_ADMIN',
  'MERCHANT_OPERATOR',
  'MERCHANT_OWNER'
);
--rollback DROP TYPE "merchant_role";

--changeset rohit.patil:003
CREATE TYPE "day_of_week" AS ENUM (
  'MONDAY',
  'TUESDAY',
  'WEDNESDAY',
  'THURSDAY',
  'FRIDAY',
  'SATURDAY',
  'SUNDAY'
);
--rollback DROP TYPE "day_of_week";

--changeset rohit.patil:004
CREATE TYPE "merchant_type" AS ENUM (
  'DINE_IN',
  'TAKE_AWAY'
);
--rollback DROP TYPE "merchant_type";

--changeset rohit.patil:005
CREATE TYPE "item_tag" AS ENUM (
  'VEG',
  'NONVEG',
  'JAIN'
);
--rollback DROP TYPE "item_tag";

--changeset rohit.patil:006
CREATE TYPE "order_status" AS ENUM (
  'ACCEPTED',
  'PREPARING',
  'COMPLETED'
);
--rollback DROP TYPE "order_status";

--changeset rohit.patil:007
CREATE TYPE "order_item_status" AS ENUM (
  'PREPARING',
  'SERVED'
);
--rollback DROP TYPE "order_item_status";

--my comment: Changeset: Create Tables
--my comment: ================================================

--changeset rohit.patil:008
CREATE TABLE "users" (
  "id" uuid PRIMARY KEY,
  "first_name" varchar NOT NULL,
  "last_name" varchar,
  "email" varchar UNIQUE NOT NULL,
  "email_verified" bool NOT NULL DEFAULT false,
  "mobile" varchar UNIQUE NOT NULL,
  "mobile_verified" bool NOT NULL DEFAULT false,
  "google_id" varchar UNIQUE,
  "role" user_role,
  "last_logout" timestamp,
  "created_at" timestamp,
  "updated_at" timestamp
);
--rollback DROP TABLE "users";

--changeset rohit.patil:009
CREATE TABLE "hashed_password" (
  "id" uuid,
  "user_id" uuid,
  "hashed_password" varchar
);
--rollback DROP TABLE "hashed_password";

--changeset rohit.patil:010
CREATE TABLE "merchant" (
  "id" uuid PRIMARY KEY,
  "restaurant_name" varchar,
  "address" uuid NOT NULL,
  "owner" uuid NOT NULL,
  "type" merchant_type NOT NULL DEFAULT 'DINE_IN',
  "is_online" bool NOT NULL DEFAULT false,
  "created_at" timestamp,
  "updated_at" timestamp
);
--rollback DROP TABLE "merchant";

--changeset rohit.patil:011
CREATE TABLE "merchant_managers" (
  "id" uuid PRIMARY KEY,
  "user_id" uuid NOT NULL,
  "merchant_id" uuid NOT NULL,
  "role" merchant_role
);
--rollback DROP TABLE "merchant_managers";

--changeset rohit.patil:012
CREATE TABLE "timing" (
  "id" uuid PRIMARY KEY,
  "merchant_id" uuid NOT NULL,
  "day_of_week" day_of_week NOT NULL,
  "open_time" time,
  "close_time" time,
  "is_closed" bool NOT NULL DEFAULT false,
  "created_at" timestamp,
  "updated_at" timestamp
);
--rollback DROP TABLE "timing";

--changeset rohit.patil:013
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
--rollback DROP TABLE "address";

--changeset rohit.patil:014
CREATE TABLE "menu_category" (
  "id" uuid PRIMARY KEY,
  "name" varchar NOT NULL,
  "merchant_id" uuid NOT NULL,
  "created_at" timestamp,
  "updated_at" timestamp
);
--rollback DROP TABLE "menu_category";

--changeset rohit.patil:015
CREATE TABLE "menu_item" (
  "id" uuid PRIMARY KEY,
  "merchant_id" uuid NOT NULL,
  "name" varchar NOT NULL,
  "description" varchar,
  "category" uuid NOT NULL,
  "tag" item_tag NOT NULL,
  "price" float NOT NULL,
  "image_url" varchar,
  "created_at" timestamp,
  "updated_at" timestamp
);
--rollback DROP TABLE "menu_item";

--changeset rohit.patil:016
CREATE TABLE "merchant_table" (
  "id" uuid PRIMARY KEY,
  "merchant_id" uuid NOT NULL,
  "table_number" int NOT NULL,
  "created_at" timestamp,
  "updated_at" timestamp
);
--rollback DROP TABLE "merchant_table";

--changeset rohit.patil:017
CREATE TABLE "qr_code" (
  "id" uuid PRIMARY KEY,
  "table_id" uuid,
  "merchant_id" uuid NOT NULL,
  "created_at" timestamp,
  "updated_at" timestamp
);
--rollback DROP TABLE "qr_code";

--changeset rohit.patil:018
CREATE TABLE "order" (
  "id" uuid PRIMARY KEY,
  "token" integer,
  "merchant_id" uuid NOT NULL,
  "table_id" uuid,
  "status" order_status NOT NULL DEFAULT 'ACCEPTED',
  "total_amount" float,
  "created_at" timestamp,
  "updated_at" timestamp
);
--rollback DROP TABLE "order";

--changeset rohit.patil:019
CREATE TABLE "order_item" (
  "id" uuid PRIMARY KEY,
  "item_id" uuid NOT NULL,
  "order_id" uuid NOT NULL,
  "instruction" varchar,
  "quantity" int,
  "amount" float,
  "status" order_item_status NOT NULL DEFAULT 'PREPARING',
  "created_at" timestamp
);
--rollback DROP TABLE "order_item";

--my comment: Changeset: Add Indexes
--my comment: ================================================

--changeset rohit.patil:020
CREATE UNIQUE INDEX ON "timing" ("merchant_id", "day_of_week");
--rollback DROP INDEX "timing_merchant_id_day_of_week_idx";

--changeset rohit.patil:021
CREATE UNIQUE INDEX ON "merchant_table" ("merchant_id", "table_number");
--rollback DROP INDEX "merchant_table_merchant_id_table_number_idx";

--my comment: Changeset: Add Foreign Keys
--my comment: ================================================

--changeset rohit.patil:022
ALTER TABLE "hashed_password" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");
--rollback ALTER TABLE "hashed_password" DROP CONSTRAINT "hashed_password_user_id_fkey";

--changeset rohit.patil:023
ALTER TABLE "merchant" ADD FOREIGN KEY ("address") REFERENCES "address" ("id");
--rollback ALTER TABLE "merchant" DROP CONSTRAINT "merchant_address_fkey";

--changeset rohit.patil:024
ALTER TABLE "merchant" ADD FOREIGN KEY ("owner") REFERENCES "users" ("id");
--rollback ALTER TABLE "merchant" DROP CONSTRAINT "merchant_owner_fkey";

--changeset rohit.patil:025
ALTER TABLE "merchant_managers" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");
--rollback ALTER TABLE "merchant_managers" DROP CONSTRAINT "merchant_managers_user_id_fkey";

--changeset rohit.patil:026
ALTER TABLE "merchant_managers" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");
--rollback ALTER TABLE "merchant_managers" DROP CONSTRAINT "merchant_managers_merchant_id_fkey";

--changeset rohit.patil:027
ALTER TABLE "timing" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");
--rollback ALTER TABLE "timing" DROP CONSTRAINT "timing_merchant_id_fkey";

--changeset rohit.patil:028
ALTER TABLE "menu_category" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");
--rollback ALTER TABLE "menu_category" DROP CONSTRAINT "menu_category_merchant_id_fkey";

--changeset rohit.patil:029
ALTER TABLE "menu_item" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");
--rollback ALTER TABLE "menu_item" DROP CONSTRAINT "menu_item_merchant_id_fkey";

--changeset rohit.patil:030
ALTER TABLE "menu_item" ADD FOREIGN KEY ("category") REFERENCES "menu_category" ("id");
--rollback ALTER TABLE "menu_item" DROP CONSTRAINT "menu_item_category_fkey";

--changeset rohit.patil:031
ALTER TABLE "merchant_table" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");
--rollback ALTER TABLE "merchant_table" DROP CONSTRAINT "merchant_table_merchant_id_fkey";

--changeset rohit.patil:032
ALTER TABLE "qr_code" ADD FOREIGN KEY ("table_id") REFERENCES "merchant_table" ("id");
--rollback ALTER TABLE "qr_code" DROP CONSTRAINT "qr_code_table_id_fkey";

--changeset rohit.patil:033
ALTER TABLE "qr_code" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");
--rollback ALTER TABLE "qr_code" DROP CONSTRAINT "qr_code_merchant_id_fkey";

--changeset rohit.patil:034
ALTER TABLE "order" ADD FOREIGN KEY ("table_id") REFERENCES "merchant_table" ("id");
--rollback ALTER TABLE "order" DROP CONSTRAINT "order_table_id_fkey";

--changeset rohit.patil:035
ALTER TABLE "order" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchant" ("id");
--rollback ALTER TABLE "order" DROP CONSTRAINT "order_merchant_id_fkey";

--changeset rohit.patil:036
ALTER TABLE "order_item" ADD FOREIGN KEY ("item_id") REFERENCES "menu_item" ("id");
--rollback ALTER TABLE "order_item" DROP CONSTRAINT "order_item_item_id_fkey";

--changeset rohit.patil:037
ALTER TABLE "order_item" ADD FOREIGN KEY ("order_id") REFERENCES "order" ("id");
--rollback ALTER TABLE "order_item" DROP CONSTRAINT "order_item_order_id_fkey";