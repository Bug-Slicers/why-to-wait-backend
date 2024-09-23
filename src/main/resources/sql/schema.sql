CREATE TYPE "UserRole" AS ENUM (
  'MERCHANT_OPERATOR',
  'MERCHANT_ADMIN',
  'CUSTOMERs',
  'SUPER_ADMIN'
);

CREATE TYPE "DayOfWeek" AS ENUM (
  'MONDAY',
  'TUESDAY',
  'WEDNESDAY',
  'THURSDAY',
  'FRIDAY',
  'SATURDAY',
  'SUNDAY'
);

CREATE TYPE "MerchantType" AS ENUM (
  'DINE_IN',
  'TAKE_AWAY'
);

CREATE TYPE "ItemTag" AS ENUM (
  'VEG',
  'NONVEG',
  'JAIN'
);

CREATE TYPE "OrderStatus" AS ENUM (
  'ACCEPTED',
  'PREPARING',
  'COMPLETED'
);

CREATE TYPE "OrderItemStatus" AS ENUM (
  'PREPARING',
  'SERVED'
);

CREATE TABLE "Users" (
  "id" uuid PRIMARY KEY,
  "firstName" varchar2 NOT NULL,
  "lastName" varchar2,
  "email" varchar2 UNIQUE NOT NULL,
  "emailVerified" bool DEFAULT false,
  "mobile" varchar2 UNIQUE NOT NULL,
  "mobileVerified" bool DEFAULT false,
  "googleId" varchar2 UNIQUE,
  "role" UserRole,
  "lastLogout" timestamp,
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "HashedPassword" (
  "id" uuid,
  "userId" uuid,
  "hashedPassword" varchar2
);

CREATE TABLE "Merchant" (
  "id" uuid PRIMARY KEY,
  "restaurantName" varchar2,
  "address" uuid NOT NULL,
  "owner" uuid NOT NULL,
  "type" MerchantType NOT NULL DEFAULT 'DINE_IN',
  "isOnline" bool DEFAULT false,
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "Timing" (
  "id" uuid PRIMARY KEY,
  "merchantId" uuid NOT NULL,
  "dayOfWeek" DayOfWeek NOT NULL,
  "openTime" time,
  "closeTime" time,
  "isClosed" bool DEFAULT false,
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "Address" (
  "id" uuid PRIMARY KEY,
  "addressLine1" varchar2 NOT NULL,
  "addressLine2" varchar2,
  "city" varchar2,
  "district" varchar2,
  "state" varchar2,
  "pincode" varchar2(6),
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "MenuCategory" (
  "id" uuid PRIMARY KEY,
  "name" varchar2 NOT NULL,
  "merchantId" uuid NOT NULL,
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "MenuItem" (
  "id" uuid PRIMARY KEY,
  "merchantId" uuid NOT NULL,
  "name" varchar2 NOT NULL,
  "description" varchar2,
  "category" uuid NOT NULL,
  "tag" ItemTag NOT NULL,
  "price" float NOT NULL,
  "imageUrl" varchar2,
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "Table" (
  "id" uuid PRIMARY KEY,
  "merchantId" uuid NOT NULL,
  "tableNumber" int NOT NULL,
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "QrCode" (
  "id" uuid PRIMARY KEY,
  "tableId" uuid,
  "merchantId" uuid NOT NULL,
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "Order" (
  "id" uuid PRIMARY KEY,
  "token" integer,
  "merchantId" uuid NOT NULL,
  "tableId" uuid,
  "status" OrderStatus DEFAULT 'ACCEPTED',
  "totalAmout" float,
  "createdAt" timestamp,
  "updatedAt" timestamp
);

CREATE TABLE "OrderItem" (
  "id" uuid PRIMARY KEY,
  "itemId" uuid NOT NULL,
  "orderId" uuid NOT NULL,
  "instruction" varchar2,
  "quantity" int,
  "amount" float,
  "status" OrderItemStatus DEFAULT 'PREPARING',
  "createdAt" timestamp
);

CREATE UNIQUE INDEX ON "Timing" ("merchantId", "dayOfWeek");

CREATE UNIQUE INDEX ON "Table" ("merchantId", "tableNumber");

ALTER TABLE "HashedPassword" ADD FOREIGN KEY ("userId") REFERENCES "Users" ("id");

ALTER TABLE "Merchant" ADD FOREIGN KEY ("address") REFERENCES "Address" ("id");

ALTER TABLE "Merchant" ADD FOREIGN KEY ("owner") REFERENCES "Users" ("id");

ALTER TABLE "Timing" ADD FOREIGN KEY ("merchantId") REFERENCES "Merchant" ("id");

ALTER TABLE "MenuCategory" ADD FOREIGN KEY ("merchantId") REFERENCES "Merchant" ("id");

ALTER TABLE "MenuItem" ADD FOREIGN KEY ("merchantId") REFERENCES "Merchant" ("id");

ALTER TABLE "MenuItem" ADD FOREIGN KEY ("category") REFERENCES "MenuCategory" ("id");

ALTER TABLE "Table" ADD FOREIGN KEY ("merchantId") REFERENCES "Merchant" ("id");

ALTER TABLE "QrCode" ADD FOREIGN KEY ("tableId") REFERENCES "Table" ("id");

ALTER TABLE "QrCode" ADD FOREIGN KEY ("merchantId") REFERENCES "Merchant" ("id");

ALTER TABLE "Order" ADD FOREIGN KEY ("merchantId") REFERENCES "Merchant" ("id");

ALTER TABLE "Order" ADD FOREIGN KEY ("tableId") REFERENCES "Table" ("id");

ALTER TABLE "OrderItem" ADD FOREIGN KEY ("itemId") REFERENCES "MenuItem" ("id");

ALTER TABLE "OrderItem" ADD FOREIGN KEY ("orderId") REFERENCES "Order" ("id");