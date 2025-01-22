--liquibase formatted sql

--my comment: Changeset: Delete Table QrCode
--my comment: ================================================

--changeset manas.jadhav:401
ALTER TABLE "qr_code" DROP CONSTRAINT "qr_code_table_id_fkey";
--rollback ALTER TABLE "qr_code" ADD FOREIGN KEY ("table_id") REFERENCES "merchant_table" ("id");

--changeset manas.jadhav:402
DROP TABLE IF EXISTS qr_code;
--rollback CREATE TABLE "qr_code" ( "id" uuid PRIMARY KEY, "table_id" uuid, "merchant_id" uuid NOT NULL,"created_at" timestamp, "updated_at" timestamp);

