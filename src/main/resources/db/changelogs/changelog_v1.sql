-- liquibase formatted sql

-- changeset manas.jadhav:1727813630278-1
CREATE TABLE "users" ("id" UUID NOT NULL, "created_at" TIMESTAMP WITH TIME ZONE, "email" VARCHAR(255) NOT NULL, "first_name" VARCHAR(255), "google_id" VARCHAR(255), "is_email_verified" BOOLEAN DEFAULT FALSE NOT NULL, "is_mobile_verified" BOOLEAN DEFAULT FALSE NOT NULL, "last_logout" TIMESTAMP WITH TIME ZONE, "lastname" VARCHAR(255), "mobile" VARCHAR(255) NOT NULL, "updated_at" TIMESTAMP WITH TIME ZONE, CONSTRAINT "users_pkey" PRIMARY KEY ("id"));

-- changeset manas.jadhav:1727813630278-2
ALTER TABLE "users" ADD CONSTRAINT "uk63cf888pmqtt5tipcne79xsbm" UNIQUE ("mobile");

-- changeset manas.jadhav:1727813630278-3
ALTER TABLE "users" ADD CONSTRAINT "uk6dotkott2kjsp8vw4d0m25fb7" UNIQUE ("email");

-- changeset manas.jadhav:1727813630278-4
ALTER TABLE "users" ADD CONSTRAINT "ukovh8xmu9ac27t18m56gri58i1" UNIQUE ("google_id");

