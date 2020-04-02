--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 12.1

-- Started on 2020-04-02 16:53:06 +0630

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 16409)
-- Name: novid; Type: SCHEMA; Schema: -; Owner: jdev_software
--

CREATE SCHEMA novid;


ALTER SCHEMA novid OWNER TO jdev_software;

SET default_tablespace = '';

--
-- TOC entry 196 (class 1259 OID 16410)
-- Name: bis_account; Type: TABLE; Schema: novid; Owner: jdev_software
--

CREATE TABLE novid.bis_account (
    user_id bigint NOT NULL,
    secret_key character varying NOT NULL,
    keygen_date bigint NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer
);


ALTER TABLE novid.bis_account OWNER TO jdev_software;

--
-- TOC entry 197 (class 1259 OID 16416)
-- Name: bis_device; Type: TABLE; Schema: novid; Owner: jdev_software
--

CREATE TABLE novid.bis_device (
    location_id bigint NOT NULL,
    submitted_at bigint NOT NULL,
    collected_at bigint NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer,
    source_id bigint NOT NULL,
    near_by_user_id bigint NOT NULL
);


ALTER TABLE novid.bis_device OWNER TO jdev_software;

--
-- TOC entry 198 (class 1259 OID 16422)
-- Name: bis_location; Type: TABLE; Schema: novid; Owner: jdev_software
--

CREATE TABLE novid.bis_location (
    location_id bigint NOT NULL,
    submitted_at bigint NOT NULL,
    collected_at character varying NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer,
    lat numeric(17,0) NOT NULL,
    lng numeric(17,0) NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE novid.bis_location OWNER TO jdev_software;

--
-- TOC entry 199 (class 1259 OID 16428)
-- Name: bis_user; Type: TABLE; Schema: novid; Owner: jdev_software
--

CREATE TABLE novid.bis_user (
    user_id bigint NOT NULL,
    name character varying NOT NULL,
    nric character varying NOT NULL,
    mobile character varying NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer,
    registered_date bigint NOT NULL
);


ALTER TABLE novid.bis_user OWNER TO jdev_software;

--
-- TOC entry 200 (class 1259 OID 16434)
-- Name: sys_verification; Type: TABLE; Schema: novid; Owner: jdev_software
--

CREATE TABLE novid.sys_verification (
    verification_id bigint NOT NULL,
    mobile character varying NOT NULL,
    code character varying NOT NULL,
    attempt smallint DEFAULT 0 NOT NULL,
    request_count smallint DEFAULT 0 NOT NULL,
    locked_until bigint,
    expired_at bigint NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer
);


ALTER TABLE novid.sys_verification OWNER TO jdev_software;

--
-- TOC entry 3702 (class 2606 OID 16443)
-- Name: bis_account bis_account_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.bis_account
    ADD CONSTRAINT bis_account_pk PRIMARY KEY (user_id);


--
-- TOC entry 3704 (class 2606 OID 16445)
-- Name: bis_device bis_device_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.bis_device
    ADD CONSTRAINT bis_device_pk PRIMARY KEY (location_id);


--
-- TOC entry 3707 (class 2606 OID 16447)
-- Name: bis_location bis_location_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.bis_location
    ADD CONSTRAINT bis_location_pk PRIMARY KEY (location_id);


--
-- TOC entry 3711 (class 2606 OID 16449)
-- Name: bis_user bis_user_mobile_unq; Type: CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.bis_user
    ADD CONSTRAINT bis_user_mobile_unq UNIQUE (mobile);


--
-- TOC entry 3714 (class 2606 OID 16451)
-- Name: bis_user bis_user_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.bis_user
    ADD CONSTRAINT bis_user_pk PRIMARY KEY (user_id);


--
-- TOC entry 3717 (class 2606 OID 16453)
-- Name: sys_verification sys_verification_mobile_unq; Type: CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.sys_verification
    ADD CONSTRAINT sys_verification_mobile_unq UNIQUE (mobile);


--
-- TOC entry 3719 (class 2606 OID 16455)
-- Name: sys_verification sys_verification_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.sys_verification
    ADD CONSTRAINT sys_verification_pk PRIMARY KEY (verification_id);


--
-- TOC entry 3705 (class 1259 OID 16458)
-- Name: bis_device_source_id_idx; Type: INDEX; Schema: novid; Owner: jdev_software
--

CREATE INDEX bis_device_source_id_idx ON novid.bis_device USING btree (source_id);


--
-- TOC entry 3708 (class 1259 OID 16460)
-- Name: bis_location_user_id_idx; Type: INDEX; Schema: novid; Owner: jdev_software
--

CREATE INDEX bis_location_user_id_idx ON novid.bis_location USING btree (user_id);


--
-- TOC entry 3709 (class 1259 OID 16461)
-- Name: bis_user_mobile_idx; Type: INDEX; Schema: novid; Owner: jdev_software
--

CREATE INDEX bis_user_mobile_idx ON novid.bis_user USING btree (mobile);


--
-- TOC entry 3712 (class 1259 OID 16462)
-- Name: bis_user_nric_idx; Type: INDEX; Schema: novid; Owner: jdev_software
--

CREATE INDEX bis_user_nric_idx ON novid.bis_user USING btree (nric);


--
-- TOC entry 3715 (class 1259 OID 16463)
-- Name: sys_verification_mobile_idx; Type: INDEX; Schema: novid; Owner: jdev_software
--

CREATE INDEX sys_verification_mobile_idx ON novid.sys_verification USING btree (mobile);


--
-- TOC entry 3720 (class 2606 OID 16464)
-- Name: bis_account bis_account_fk; Type: FK CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.bis_account
    ADD CONSTRAINT bis_account_fk FOREIGN KEY (user_id) REFERENCES novid.bis_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3721 (class 2606 OID 16480)
-- Name: bis_device bis_device_fk; Type: FK CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.bis_device
    ADD CONSTRAINT bis_device_fk FOREIGN KEY (source_id) REFERENCES novid.bis_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3722 (class 2606 OID 16475)
-- Name: bis_location bis_location_fk; Type: FK CONSTRAINT; Schema: novid; Owner: jdev_software
--

ALTER TABLE ONLY novid.bis_location
    ADD CONSTRAINT bis_location_fk FOREIGN KEY (user_id) REFERENCES novid.bis_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3849 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA novid; Type: ACL; Schema: -; Owner: jdev_software
--

GRANT USAGE ON SCHEMA novid TO novid_owner;


--
-- TOC entry 3850 (class 0 OID 0)
-- Dependencies: 196
-- Name: TABLE bis_account; Type: ACL; Schema: novid; Owner: jdev_software
--

GRANT ALL ON TABLE novid.bis_account TO novid_owner;


--
-- TOC entry 3851 (class 0 OID 0)
-- Dependencies: 197
-- Name: TABLE bis_device; Type: ACL; Schema: novid; Owner: jdev_software
--

GRANT ALL ON TABLE novid.bis_device TO novid_owner;


--
-- TOC entry 3852 (class 0 OID 0)
-- Dependencies: 198
-- Name: TABLE bis_location; Type: ACL; Schema: novid; Owner: jdev_software
--

GRANT ALL ON TABLE novid.bis_location TO novid_owner;


--
-- TOC entry 3853 (class 0 OID 0)
-- Dependencies: 199
-- Name: TABLE bis_user; Type: ACL; Schema: novid; Owner: jdev_software
--

GRANT ALL ON TABLE novid.bis_user TO novid_owner;


--
-- TOC entry 3854 (class 0 OID 0)
-- Dependencies: 200
-- Name: TABLE sys_verification; Type: ACL; Schema: novid; Owner: jdev_software
--

GRANT ALL ON TABLE novid.sys_verification TO novid_owner;


-- Completed on 2020-04-02 16:53:11 +0630

--
-- PostgreSQL database dump complete
--

