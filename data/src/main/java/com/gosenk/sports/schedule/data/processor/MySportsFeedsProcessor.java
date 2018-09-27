package com.gosenk.sports.schedule.data.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosenk.sports.schedule.common.entity.Game;
import com.gosenk.sports.schedule.common.entity.Team;
import com.gosenk.sports.schedule.common.entity.Venue;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

abstract class MySportsFeedsProcessor extends BaseProcessor {
    private static final String SCHEMA_NAME = "sports_schedule";
    private static final String API_VERSION = "v2.0";

    private String leagueId;
    private String apiLeagueIdentifier;
    private String apiSeasonDescription;

    private static String seasonalGamesEndpoint = "https://api.mysportsfeeds.com/{0}/pull/{1}/{2}/games.json";

    private ObjectMapper objectMapper = new ObjectMapper();
    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");

    public MySportsFeedsProcessor(String leagueId, String apiLeagueIdentifier, String apiSeasonDescription){
        this.leagueId = leagueId;
        this.apiLeagueIdentifier = apiLeagueIdentifier;
        this.apiSeasonDescription = apiSeasonDescription;
    }

    // TODO Don't bubble exception up, fail only portion
    public void process() throws Exception {
        // Get data
        ResponseEntity<String> response;
        try {
             response = getData();
        } catch(Exception e){
            e.printStackTrace();
            return;
        }

        if(response.getStatusCode().is2xxSuccessful()){
            // Split Games and References (Teams, Venues)
            JSONObject wrapper = new JSONObject(response.getBody());

            // Last Updated On
            Date lastUpdatedOn = sdf.parse(wrapper.get("lastUpdatedOn").toString());

            // Games
            JSONArray games = wrapper.getJSONArray("games");

            // References
            JSONObject references = wrapper.getJSONObject("references");
            JSONArray teamReferences = references.getJSONArray("teamReferences");
            JSONArray venueReferences = references.getJSONArray("venueReferences");

            processVenues(venueReferences);
            processTeams(teamReferences);
            processGames(games);
        } else {
            System.out.println(response.getStatusCode());
            // TODO Log Error
        }
    }

    private static final String BASE_VENUE_INSERT = "INSERT IGNORE INTO venue (id,name,city,country) VALUES ";
    private void processVenues(JSONArray venues) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(leagueId.toLowerCase() + "_venue.sql");

        if(venues.length() == 0) {
            outputStream.write("Error: No Venues!".getBytes());
        } else {
            outputStream.write(("USE " + SCHEMA_NAME + ";").getBytes());

            outputStream.write("\n".getBytes());
            outputStream.write(BASE_VENUE_INSERT.getBytes());
            outputStream.write("\n".getBytes());

            StringBuilder sb = new StringBuilder();

            int i = 0;
            for (Object obj : venues) {
                i++;
                JSONObject venueObj = (JSONObject) obj;

                Venue venue = objectMapper.readValue(venueObj.toString(), Venue.class);

                sb.append("(")
                        .append(venue.getId()).append(",")
                        .append(createStringFieldValue(venue.getName())).append(",")
                        .append(createStringFieldValue(venue.getCity())).append(",")
                        .append(createStringFieldValue(venue.getCountry()))
                        .append(")");

                if(i == venues.length()){
                    sb.append(";");
                } else {
                    sb.append(",").append("\n");
                }

                outputStream.write(sb.toString().getBytes());

                sb.setLength(0);
            }
        }

        outputStream.close();
    }

    private static final String BASE_TEAM_INSERT = "INSERT INTO team (id,city,name,abbreviation,league_id,venue_id) VALUES ";
    private void processTeams(JSONArray teams) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(leagueId.toLowerCase() + "_team.sql");

        if(teams.length() == 0) {
            outputStream.write("Error: No Teams!".getBytes());
        } else {
            outputStream.write(("USE " + SCHEMA_NAME + ";").getBytes());

            outputStream.write("\n".getBytes());
            outputStream.write(BASE_TEAM_INSERT.getBytes());
            outputStream.write("\n".getBytes());

            StringBuilder sb = new StringBuilder();

            int i = 0;
            for (Object obj : teams) {
                i++;
                JSONObject teamObj = (JSONObject) obj;

                Team team = objectMapper.readValue(teamObj.toString(), Team.class);

                sb.append("(")
                        .append(team.getId()).append(",")
                        .append(createStringFieldValue(team.getCity())).append(",")
                        .append(createStringFieldValue(team.getName())).append(",")
                        .append(createStringFieldValue(team.getAbbreviation())).append(",")
                        .append(createStringFieldValue(leagueId)).append(",")
                        .append(team.getHomeVenue().getId())
                        .append(")");

                if(i == teams.length()){
                    sb.append(";");
                } else {
                    sb.append(",").append("\n");
                }

                outputStream.write(sb.toString().getBytes());

                sb.setLength(0);
            }
        }

        outputStream.close();
    }

    private static final String BASE_GAME_INSERT = "INSERT INTO game (id,week,start_time,away_team_id,home_team_id,venue_id,venue_allegiance,schedule_status,original_start_time,delayed_or_postponed_reason,played_status) VALUES ";
    private void processGames(JSONArray games) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(leagueId.toLowerCase() + "_game.sql");

        if(games.length() == 0) {
            outputStream.write("Error: No Games!".getBytes());
        } else {
            outputStream.write(("USE " + SCHEMA_NAME + ";").getBytes());

            outputStream.write("\n".getBytes());
            outputStream.write(BASE_GAME_INSERT.getBytes());
            outputStream.write("\n".getBytes());

            StringBuilder sb = new StringBuilder();

            int i = 0;
            for (Object obj : games) {
                i++;
                JSONObject gameObj = (JSONObject) obj;

                JSONObject scheduleObj = gameObj.getJSONObject("schedule");
                //JSONObject scoreObj = gameObj.getJSONObject("score"); // Not used atm

                Game game = objectMapper.readValue(scheduleObj.toString(), Game.class);

                sb.append("(")
                        .append(game.getId()).append(",")
                        .append(game.getWeek()).append(",")
                        .append(createMySQLDateString(game.getStartTime())).append(",")
                        .append(game.getAwayTeam().getId()).append(",")
                        .append(game.getHomeTeam().getId()).append(",")
                        .append(game.getVenue().getId()).append(",")
                        .append(createStringFieldValue(game.getVenueAllegiance())).append(",")
                        .append(createStringFieldValue(game.getScheduleStatus())).append(",")
                        .append(createMySQLDateString(game.getOriginalStartTime())).append(",")
                        .append(createStringFieldValue(game.getDelayedOrPostponedReason())).append(",")
                        .append(createStringFieldValue(game.getPlayedStatus()))
                        .append(")");

                if(i == games.length()){
                    sb.append(";");
                } else {
                    sb.append(",").append("\n");
                }

                outputStream.write(sb.toString().getBytes());

                sb.setLength(0);
            }
        }

        outputStream.close();
    }

    private ResponseEntity<String> getData(){
        RestTemplateBuilder restTemplate = new RestTemplateBuilder();
        restTemplate.setConnectTimeout(30000); // 30 Seconds

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + getAuthEncoded());

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.build().exchange(getUrl(), HttpMethod.GET, httpEntity, String.class);
        } catch (Exception e){
            System.out.println("Error Calling [" + getUrl() + "]");
            throw e;
        }

        return response;
    }

    @Value("${mysportsfeeds.key}")
    private String mySportsFeedsKey;

    @Value("${mysportsfeeds.password}")
    private String mySportsFeedsPassword;

    private String getAuthEncoded(){
        byte[] auth = (mySportsFeedsKey + ":" + mySportsFeedsPassword).getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(auth); // Do NOT commit the token, store elsewhere
    }

    private String getUrl(){
        Object[] params = new Object[]{API_VERSION, apiLeagueIdentifier, apiSeasonDescription};
        return MessageFormat.format(seasonalGamesEndpoint, params);
    }

    private String createStringFieldValue(String value){
        if(StringUtils.isEmpty(value)){
            return "null";
        }

        return "'" + StringUtils.replace(value, "'", "''") + "'";
    }

    private SimpleDateFormat mysqlSDF = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    private String createMySQLDateString(Date date){
        if(date == null){
            return "null";
        }

        return createStringFieldValue(mysqlSDF.format(date));
    }
}
