-- ======================================================================================================
-- This module is to satisfy the requirements of Aerospike account repository.
-- In Aerospike Java client, we can put only one filter. Therefore, for some queries which need more than
-- one search criteria, we have to use UDF.
-- ======================================================================================================

-- Namespace              : qchat
-- Set name               : radar
-- Supporting Java Code   : es.qchat.radar.infrastructure.aerospike.repository.UserLocationRecordRepository.java
-- Maven Dependency Name  : am-radar.jar

-- MANUAL :
-- How to register this module into Aerospike and run :
-- 1. Copy this radar.lua to /opt/aerospike/usr/udf/lua folder
-- 2. In aql, use 'register' command to register the module
--
--    REGISTER MODULE '/opt/aerospike/usr/udf/lua/radar.lua'
--    **Make sure you put the full path.
--
-- 3. Then run this query :
--
--    CREATE INDEX idx_user_location_geo_loc ON qchat.user_location (geo_loc) GEO2DSPHERE;
--    AGGREGATE radar.findUser('F', 'Y') ON qchat.user_location WHERE prvder_id = "fbuserid"



-- **IMPORTANT**
-- If there is any modification to any of these bin names, please make sure you update these information in
-- your aerospike record classes (es.qchat.user.infrastructure.aerospike.record.UserRecord).

local BinNames = map {

    -- UserLocation
    personId = "p_id",
    gender = "gender",
    name = "name",
    username = "username",
    dob = "dob",
    location = "location",
    password = "pwd"
}

-- To find AccountEntity using provider info.
function find_by_username_and_password (stream, username, password)

  local function filter_username (rec)
    info("filter_username : %s", username)
    return rec[BinNames.username] == username
  end

  -- To filter data using visiblity
  -- visibility : Y (Visible), N (Not Visible)
  local function filter_password (rec)
    info("filter_password : %s", password)
    return rec[BinNames.password] == password
  end

  local function mapper(rec)

    local mappedResult = map()
    
    mappedResult[BinNames.personId] = rec[BinNames.personId]
    mappedResult[BinNames.gender] = rec[BinNames.gender]
    mappedResult[BinNames.name] = rec[BinNames.name]
    mappedResult[BinNames.username] = rec[BinNames.username]
    mappedResult[BinNames.dob] = rec[BinNames.dob]
    mappedResult[BinNames.location] = rec[BinNames.location]
    mappedResult[BinNames.password] = rec[BinNames.password]

    info("mappedResult : %s", tostring(mappedResult))
    
    return mappedResult
  end

  return stream : filter(filter_username) : filter(filter_password) : map(mapper)
end
