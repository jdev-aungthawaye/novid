--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2020-03-31 22:22:55 +0630

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
-- TOC entry 7 (class 2615 OID 18342)
-- Name: novid; Type: SCHEMA; Schema: -; Owner: novid_owner
--

CREATE SCHEMA novid;


ALTER SCHEMA novid OWNER TO novid_owner;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 236 (class 1259 OID 18357)
-- Name: bis_account; Type: TABLE; Schema: novid; Owner: jdev_sysdba
--

CREATE TABLE novid.bis_account (
    user_id bigint NOT NULL,
    secret_key character varying NOT NULL,
    keygen_date bigint NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer
);


ALTER TABLE novid.bis_account OWNER TO jdev_sysdba;

--
-- TOC entry 239 (class 1259 OID 18412)
-- Name: bis_device; Type: TABLE; Schema: novid; Owner: jdev_sysdba
--

CREATE TABLE novid.bis_device (
    location_id bigint NOT NULL,
    self_device character varying NOT NULL,
    near_by_device character varying NOT NULL,
    device_name character varying NOT NULL,
    lat numeric(17,0) NOT NULL,
    lng numeric(17,0) NOT NULL,
    submitted_at bigint NOT NULL,
    collected_at bigint NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer,
    source_id bigint NOT NULL
);


ALTER TABLE novid.bis_device OWNER TO jdev_sysdba;

--
-- TOC entry 237 (class 1259 OID 18378)
-- Name: bis_location; Type: TABLE; Schema: novid; Owner: jdev_sysdba
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
    user_id bigint NOT NULL,
    mac character varying NOT NULL
);


ALTER TABLE novid.bis_location OWNER TO jdev_sysdba;

--
-- TOC entry 235 (class 1259 OID 18343)
-- Name: bis_user; Type: TABLE; Schema: novid; Owner: jdev_sysdba
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


ALTER TABLE novid.bis_user OWNER TO jdev_sysdba;

--
-- TOC entry 238 (class 1259 OID 18389)
-- Name: sys_verification; Type: TABLE; Schema: novid; Owner: jdev_sysdba
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


ALTER TABLE novid.sys_verification OWNER TO jdev_sysdba;

--
-- TOC entry 3207 (class 2606 OID 18361)
-- Name: bis_account bis_account_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_account
    ADD CONSTRAINT bis_account_pk PRIMARY KEY (user_id);


--
-- TOC entry 3219 (class 2606 OID 18419)
-- Name: bis_device bis_device_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_device
    ADD CONSTRAINT bis_device_pk PRIMARY KEY (location_id);


--
-- TOC entry 3210 (class 2606 OID 26606)
-- Name: bis_location bis_location_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_location
    ADD CONSTRAINT bis_location_pk PRIMARY KEY (location_id);


--
-- TOC entry 3202 (class 2606 OID 18352)
-- Name: bis_user bis_user_mobile_unq; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_user
    ADD CONSTRAINT bis_user_mobile_unq UNIQUE (mobile);


--
-- TOC entry 3205 (class 2606 OID 18350)
-- Name: bis_user bis_user_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_user
    ADD CONSTRAINT bis_user_pk PRIMARY KEY (user_id);


--
-- TOC entry 3214 (class 2606 OID 18400)
-- Name: sys_verification sys_verification_mobile_unq; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.sys_verification
    ADD CONSTRAINT sys_verification_mobile_unq UNIQUE (mobile);


--
-- TOC entry 3216 (class 2606 OID 18398)
-- Name: sys_verification sys_verification_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.sys_verification
    ADD CONSTRAINT sys_verification_pk PRIMARY KEY (verification_id);


--
-- TOC entry 3217 (class 1259 OID 18421)
-- Name: bis_device_near_by_device_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_device_near_by_device_idx ON novid.bis_device USING btree (near_by_device);


--
-- TOC entry 3220 (class 1259 OID 18420)
-- Name: bis_device_self_device_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_device_self_device_idx ON novid.bis_device USING btree (self_device);


--
-- TOC entry 3221 (class 1259 OID 26604)
-- Name: bis_device_source_id_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_device_source_id_idx ON novid.bis_device USING btree (source_id);


--
-- TOC entry 3208 (class 1259 OID 26608)
-- Name: bis_location_mac_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_location_mac_idx ON novid.bis_location USING btree (mac);


--
-- TOC entry 3211 (class 1259 OID 26607)
-- Name: bis_location_user_id_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_location_user_id_idx ON novid.bis_location USING btree (user_id);


--
-- TOC entry 3200 (class 1259 OID 18355)
-- Name: bis_user_mobile_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_user_mobile_idx ON novid.bis_user USING btree (mobile);


--
-- TOC entry 3203 (class 1259 OID 18356)
-- Name: bis_user_nric_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_user_nric_idx ON novid.bis_user USING btree (nric);


--
-- TOC entry 3212 (class 1259 OID 18401)
-- Name: sys_verification_mobile_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX sys_verification_mobile_idx ON novid.sys_verification USING btree (mobile);


--
-- TOC entry 3222 (class 2606 OID 18362)
-- Name: bis_account bis_account_fk; Type: FK CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_account
    ADD CONSTRAINT bis_account_fk FOREIGN KEY (user_id) REFERENCES novid.bis_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3223 (class 2606 OID 18384)
-- Name: bis_location bis_location_fk; Type: FK CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_location
    ADD CONSTRAINT bis_location_fk FOREIGN KEY (location_id) REFERENCES novid.bis_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3355 (class 0 OID 0)
-- Dependencies: 236
-- Name: TABLE bis_account; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.bis_account TO novid_owner;


--
-- TOC entry 3356 (class 0 OID 0)
-- Dependencies: 239
-- Name: TABLE bis_device; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.bis_device TO novid_owner;


--
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 237
-- Name: TABLE bis_location; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.bis_location TO novid_owner;


--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 235
-- Name: TABLE bis_user; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.bis_user TO novid_owner;


--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 238
-- Name: TABLE sys_verification; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.sys_verification TO novid_owner;


-- Completed on 2020-03-31 22:22:56 +0630

--
-- PostgreSQL database dump complete
--

