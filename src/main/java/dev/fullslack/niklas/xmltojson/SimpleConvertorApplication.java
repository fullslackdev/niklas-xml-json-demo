package dev.fullslack.niklas.xmltojson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleConvertorApplication {

    private static final int INDENTATION = 4;
    private static final String XML_STRING =
            "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>" +
            "<root>" +
                "<firstName>John</firstName>" +
                "<lastName>Snow</lastName>" +
                "<age>25</age>" +
                "<spouse/>" +
                "<address>" +
                    "<street>237 Harrison Street</street>" +
                    "<city>Brooklyn, NY</city>" +
                    "<state>New York</state>" +
                    "<postalCode>11238</postalCode>" +
                "</address>" +
                "<phoneNumbers>" +
                    "<type>mobile</type>" +
                    "<number>212 555-3346</number>" +
                "</phoneNumbers>" +
                "<phoneNumbers>" +
                    "<type>fax</type>" +
                    "<number>646 555-4567</number>" +
                "</phoneNumbers>" +
            "</root>";

    public static void main(String[] args) {
        try {
//            xmlStringToJson();
            xmlFileToJson("target/companies");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void xmlStringToJson() throws JSONException {
        JSONObject jsonObject = XML.toJSONObject(XML_STRING);
        String json = jsonObject.toString(INDENTATION);

        System.out.println(json);
    }

    private static void xmlFileToJson(String fileName) throws JSONException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName + ".json"));
             BufferedReader reader = Files.newBufferedReader(Paths.get(fileName + ".xml"))) {
            JSONObject jsonObject = XML.toJSONObject(reader);
            JSONArray jsonArray = jsonObject.getJSONObject("companies").getJSONArray("company");
            AtomicInteger id = new AtomicInteger(1);
            jsonArray.forEach(item -> {
                JSONObject company = (JSONObject) item;
                company.remove("uuid");
                company.remove("url");
                company.put("id", Integer.parseInt(id.toString()));
                id.incrementAndGet();
            });

            writer.write(jsonObject.toString(INDENTATION));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
