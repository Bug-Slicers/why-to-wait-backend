--liquibase formatted sql

--my comment: Changeset: Add is_available column to menu_item
--my comment: ================================================

--changeset rohit.patil:202
ALTER TABLE menu_item ADD COLUMN is_available BOOLEAN NOT NULL DEFAULT TRUE;
--rollback ALTER TABLE menu_item DROP COLUMN is_available;
