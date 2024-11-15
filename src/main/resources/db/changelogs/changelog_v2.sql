--liquibase formatted sql

--changeset rohit.patil:101
CREATE CAST (varchar AS user_role) WITH INOUT AS IMPLICIT;
--rollback DROP CAST (varchar AS user_role);

--my comment: Changeset: Create Casts
--my comment: ================================================

--changeset rohit.patil:102
CREATE CAST (varchar AS merchant_role) WITH INOUT AS IMPLICIT;
--rollback DROP CAST (varchar AS merchant_role);

--changeset rohit.patil:103
CREATE CAST (varchar AS day_of_week) WITH INOUT AS IMPLICIT;
--rollback DROP CAST (varchar AS day_of_week);

--changeset rohit.patil:104
CREATE CAST (varchar AS merchant_type) WITH INOUT AS IMPLICIT;
--rollback DROP CAST (varchar AS merchant_type);

--changeset rohit.patil:105
CREATE CAST (varchar AS item_tag) WITH INOUT AS IMPLICIT;
--rollback DROP CAST (varchar AS item_tag);

--changeset rohit.patil:106
CREATE CAST (varchar AS order_status) WITH INOUT AS IMPLICIT;
--rollback DROP CAST (varchar AS order_status);

--changeset rohit.patil:107
CREATE CAST (varchar AS order_item_status) WITH INOUT AS IMPLICIT;
--rollback DROP CAST (varchar AS order_item_status);
