-- liquibase formatted sql

-- changeset manas.jadhav:1727813630278-5

CREATE TABLE "HashedPassword" (
  "id" uuid,
  "userId" uuid,
  "hashedPassword" varchar2
);

ALTER TABLE "HashedPassword" ADD FOREIGN KEY ("userId") REFERENCES "Users" ("id");
