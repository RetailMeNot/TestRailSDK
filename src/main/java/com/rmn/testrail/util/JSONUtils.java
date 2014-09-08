package com.rmn.testrail.util;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mmerrell
 */
public class JSONUtils {

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
        mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties );

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
     */
    public static <T> List<T> getMappedJsonObjectList(Class<T> jsonObjectClass, String json) {
        return getMappedJsonObjectList(jsonObjectClass, json, true);
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
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties);
        
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

