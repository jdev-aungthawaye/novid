--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2020-03-30 11:55:14 +0630

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
    secret_key bigint NOT NULL,
    keygen_date bigint NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer
);


ALTER TABLE novid.bis_account OWNER TO jdev_sysdba;

--
-- TOC entry 237 (class 1259 OID 18378)
-- Name: bis_location; Type: TABLE; Schema: novid; Owner: jdev_sysdba
--

CREATE TABLE novid.bis_location (
    user_id bigint NOT NULL,
    submitted_at bigint NOT NULL,
    collected_at character varying NOT NULL,
    created_date bigint,
    updated_date bigint,
    version integer,
    lat numeric(17,0) NOT NULL,
    lng numeric(17,0) NOT NULL
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
-- TOC entry 3201 (class 2606 OID 18361)
-- Name: bis_account bis_account_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_account
    ADD CONSTRAINT bis_account_pk PRIMARY KEY (user_id);


--
-- TOC entry 3196 (class 2606 OID 18352)
-- Name: bis_user bis_user_mobile_unq; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_user
    ADD CONSTRAINT bis_user_mobile_unq UNIQUE (mobile);


--
-- TOC entry 3199 (class 2606 OID 18350)
-- Name: bis_user bis_user_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_user
    ADD CONSTRAINT bis_user_pk PRIMARY KEY (user_id);


--
-- TOC entry 3204 (class 2606 OID 18400)
-- Name: sys_verification sys_verification_mobile_unq; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.sys_verification
    ADD CONSTRAINT sys_verification_mobile_unq UNIQUE (mobile);


--
-- TOC entry 3206 (class 2606 OID 18398)
-- Name: sys_verification sys_verification_pk; Type: CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.sys_verification
    ADD CONSTRAINT sys_verification_pk PRIMARY KEY (verification_id);


--
-- TOC entry 3194 (class 1259 OID 18355)
-- Name: bis_user_mobile_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_user_mobile_idx ON novid.bis_user USING btree (mobile);


--
-- TOC entry 3197 (class 1259 OID 18356)
-- Name: bis_user_nric_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX bis_user_nric_idx ON novid.bis_user USING btree (nric);


--
-- TOC entry 3202 (class 1259 OID 18401)
-- Name: sys_verification_mobile_idx; Type: INDEX; Schema: novid; Owner: jdev_sysdba
--

CREATE INDEX sys_verification_mobile_idx ON novid.sys_verification USING btree (mobile);


--
-- TOC entry 3207 (class 2606 OID 18362)
-- Name: bis_account bis_account_fk; Type: FK CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_account
    ADD CONSTRAINT bis_account_fk FOREIGN KEY (user_id) REFERENCES novid.bis_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3208 (class 2606 OID 18384)
-- Name: bis_location bis_location_fk; Type: FK CONSTRAINT; Schema: novid; Owner: jdev_sysdba
--

ALTER TABLE ONLY novid.bis_location
    ADD CONSTRAINT bis_location_fk FOREIGN KEY (user_id) REFERENCES novid.bis_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3340 (class 0 OID 0)
-- Dependencies: 236
-- Name: TABLE bis_account; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.bis_account TO novid_owner;


--
-- TOC entry 3341 (class 0 OID 0)
-- Dependencies: 237
-- Name: TABLE bis_location; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.bis_location TO novid_owner;


--
-- TOC entry 3342 (class 0 OID 0)
-- Dependencies: 235
-- Name: TABLE bis_user; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.bis_user TO novid_owner;


--
-- TOC entry 3343 (class 0 OID 0)
-- Dependencies: 238
-- Name: TABLE sys_verification; Type: ACL; Schema: novid; Owner: jdev_sysdba
--

GRANT ALL ON TABLE novid.sys_verification TO novid_owner;


-- Completed on 2020-03-30 11:55:15 +0630

--
-- PostgreSQL database dump complete
--

