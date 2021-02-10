package com.rmn.testrail.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rmn.testrail.entity.BasicPaged;
import com.rmn.testrail.entity.BulkEntityCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mmerrell
 */
public class JSONUtils {

    private static final Logger log = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * Takes a JSON string and attempts to "map" it to the given class. It assumes the JSON 
     * string is valid, and that it maps to the object you've indicated, and any problem with
     * either the JSON or the specified class will result in a RuntimeException. It will run 
     * with FAIL_ON_UNKNOWN_PROPERTIES set to false, which will allow some unmapped custom fields
     * to come through without throwing exceptions. If you have custom fields on your entities, you 
     * should subclass the entity provided here and add the custom fields to it
     * @param jsonObjectClass The Class you wish to map the contents to
     * @param json The JSON you wish to map to the given Class
     * @return An instance of the given Class, based on the attributes of the given JSON
     */
    public static <T> T getMappedJsonObject(Class<T> jsonObjectClass, String json) {
        return getMappedJsonObject(jsonObjectClass, json, false);
    }
    
    /**
     * Takes a JSON string and attempts to "map" it to the given class. It assumes the JSON 
     * string is valid, and that it maps to the object you've indicated. If you want to run outside of "strict" mode,
     * pass false for the failOnUnknownProperties flag
     * @param jsonObjectClass The Class you wish to map the contents to
     * @param json The JSON you wish to map to the given Class
     * @param failOnUnknownProperties Whether or not to throw an exception if an unknown JSON attribute is encountered
     * @return An instance of the given Class, based on the attributes of the given JSON
     */
    public static <T> T getMappedJsonObject( Class<T> jsonObjectClass, String json, boolean failOnUnknownProperties ) {
        TypeFactory t = TypeFactory.defaultInstance();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES , failOnUnknownProperties );

        T mappedObject;
        try {
            mappedObject = mapper.readValue( json, t.constructType( jsonObjectClass ) );
        } catch ( Exception e ) {
            throw new RuntimeException( "Could not instantiate object of class [" + jsonObjectClass.getName()+ "]: " + e );
        }
        return mappedObject;
    }

    /**
     * Returns a list of objects of the specified type. This runs in "strict" mode, so if it encounters
     * a property in the JSON object that isn't defined or accessible in the class definition, it will
     * throw an exception. See the alternate "getJsonObjectList( Class, String boolean ) if you would
     * like it to ignore unknown properties
     * @param jsonObjectClass The Class you wish to map the json to
     * @param json The JSON you wish to map to the given Class
     * @return An instance of the given Class, based on the attributes of the given JSON
     *
     * Note: To accommodate TestRail 6.7 changes to bulk API response this method (as only strict mode used) has
     * been updated to get returned paged object and extract the json string representing entity list.  This done
     * conditionally only on types that are part of the bulk API with the help of {@link BulkEntityCategory}.
     */
    public static <T> List<T> getMappedJsonObjectList(Class<T> jsonObjectClass, String json) {
        String entityType = jsonObjectClass.getSimpleName().toLowerCase();
        log.debug("The entity type to process the list has simple name: {}", entityType);
        if(BulkEntityCategory.isBulkCategory(entityType)) {
            log.debug("The enum type used to determine if bulk: '{}'", BulkEntityCategory.fromBulkType(entityType));
            String jsonObjectList = extractObjectList(json);
            return getMappedJsonObjectList(jsonObjectClass, jsonObjectList, false);
        }
        return getMappedJsonObjectList(jsonObjectClass, json, false);
    }

    /**
     * Private helper method to extract list of entities defined in {@link BulkEntityCategory} from paged bulk api
     * defined as {@link BasicPaged}
     * @param json - The JSON object returned by paged bulk API introduced in TestRail 6.7
     * @return - Json String that represents a list of extracted entities in compliance with API before 6.7
     */
    private static String extractObjectList(String json) {
        log.debug("Attempting to parse out the list of entities from json string for paged object.");
        BasicPaged multiPage = getMappedJsonObject(BasicPaged.class, json, false);
        List<?> entitiesObjectList = multiPage.getList();
        //log.debug("Expose entity list for debugging: '{}'", entitiesObjectList);
        String jsonEntityList = "";
        try {
            jsonEntityList = new ObjectMapper().writeValueAsString(entitiesObjectList);
        } catch (JsonProcessingException ex) {
            log.debug("The list will be empty due to exception '{}'", ex.toString());
        }
        return jsonEntityList;
    }


    /**
     * Returns a list of objects of the specified type. If you send "false" in the 3rd parameter, it will be 
     * forgiving of JSON properties that are not defined or inaccessible in the specified jsonObjectClass
     * @param jsonObjectClass The Class you wish to map the json to
     * @param json The JSON you wish to map to the given Class
     * @param failOnUnknownProperties Whether or not to throw an exception if an unknown JSON attribute is encountered
     * @return An instance of the given Class, based on the attributes of the given JSON
     */
    public static <T> List<T> getMappedJsonObjectList(Class<T> jsonObjectClass, String json, boolean failOnUnknownProperties) {
        List<T> list;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties);
        
        TypeFactory t = TypeFactory.defaultInstance();
        try {
            list = mapper.readValue(json, t.constructCollectionType(ArrayList.class, jsonObjectClass));
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException("Could not process JSON");
    }
}

