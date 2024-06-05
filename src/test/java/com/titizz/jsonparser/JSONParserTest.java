package com.titizz.jsonparser;

import com.titizz.jsonparser.model.JsonArray;
import com.titizz.jsonparser.model.JsonObject;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JSONParserTest {

    @Test
    public void testFromJSON() throws Exception {
        String path = this.getClass().getResource("/university_courses.json").getFile();
        String json = new String(Files.readAllBytes(Paths.get(path)), "utf-8");

        JSONParser jsonParser = new JSONParser();
        JsonObject root = (JsonObject) jsonParser.fromJSON(json);

        JsonObject university = root.getJsonObject("university");

        JsonArray departments = university.getJsonArray("departments");
        assertEquals(1, departments.size());

        JsonObject department = departments.getJsonObject(0);
        assertEquals("Computer Science", department.get("name"));

        JsonArray courses = department.getJsonArray("courses");
        assertEquals(1, courses.size());

        JsonObject course = courses.getJsonObject(0);
        assertEquals("Artificial Intelligence", course.get("title"));

        JsonObject details = course.getJsonObject("details");
        assertEquals(150, details.get("students_enrolled"));

        JsonObject instructor = details.getJsonObject("instructor");
        assertEquals("Dr. Smith", instructor.get("name"));
        assertEquals(789, instructor.get("id"));

        JsonArray modules = details.getJsonArray("modules");
        assertEquals(2, modules.size());

        JsonObject module1 = modules.getJsonObject(0);
        assertEquals("Machine Learning", module1.get("title"));

        JsonObject module2 = modules.getJsonObject(1);
        assertEquals("Natural Language Processing", module2.get("title"));

        JsonArray topics = module1.getJsonArray("topics");
        assertEquals(1, topics.size());

        JsonObject topic1 = topics.getJsonObject(0);
        assertEquals("Supervised Learning", topic1.get("name"));

        JsonArray lectures = topic1.getJsonArray("lectures");
        assertEquals(2, lectures.size());

        JsonObject lecture1 = lectures.getJsonObject(0);
        assertEquals("Introduction to Supervised Learning", lecture1.get("title"));

        JsonObject resources1 = lecture1.getJsonObject("resources");

        JsonArray books1 = resources1.getJsonArray("books");
        assertEquals(1, books1.size());

        JsonObject book1 = books1.getJsonObject(0);
        assertEquals("John Doe", book1.get("author"));
        assertEquals("Tech Books", book1.getJsonObject("publisher").get("name"));
        assertEquals("USA", book1.getJsonObject("publisher").get("location"));
        assertEquals("+1122334455", book1.getJsonObject("publisher").getJsonObject("contacts").get("phone"));
        assertEquals("info@techbooks.com", book1.getJsonObject("publisher").getJsonObject("contacts").get("email"));



        // 省略前面的部分代码

        JsonObject lecture2 = lectures.getJsonObject(1);
        assertEquals("Advanced Supervised Learning", lecture2.get("title"));

        JsonObject resources2 = lecture2.getJsonObject("resources");
        JsonArray books2 = resources2.getJsonArray("books");
        assertEquals(1, books2.size());

        JsonObject book2 = books2.getJsonObject(0);
        assertEquals("Jane Smith", book2.get("author"));
        assertEquals("Deep Tech", book2.getJsonObject("publisher").get("name"));
        assertEquals("UK", book2.getJsonObject("publisher").get("location"));
        assertEquals("+3344556677", book2.getJsonObject("publisher").getJsonObject("contacts").get("phone"));
        assertEquals("info@deeptech.co.uk", book2.getJsonObject("publisher").getJsonObject("contacts").get("email"));

        JsonArray topics2 = module2.getJsonArray("topics");
        assertEquals(1, topics2.size());

        JsonObject topic2 = topics2.getJsonObject(0);
        assertEquals("Text Processing", topic2.get("name"));

        JsonArray lectures2 = topic2.getJsonArray("lectures");
        assertEquals(2, lectures2.size());

        JsonObject lecture3 = lectures2.getJsonObject(0);
        assertEquals("Introduction to NLP", lecture3.get("title"));

        JsonObject resources3 = lecture3.getJsonObject("resources");
        JsonArray books3 = resources3.getJsonArray("books");
        assertEquals(1, books3.size());

        JsonObject book3 = books3.getJsonObject(0);
        assertEquals("Alice Brown", book3.get("author"));
        assertEquals("AI Press", book3.getJsonObject("publisher").get("name"));
        assertEquals("Canada", book3.getJsonObject("publisher").get("location"));
        assertEquals("+4455667788", book3.getJsonObject("publisher").getJsonObject("contacts").get("phone"));
        assertEquals("info@aipress.ca", book3.getJsonObject("publisher").getJsonObject("contacts").get("email"));

        JsonObject lecture4 = lectures2.getJsonObject(1);
        assertEquals("Advanced NLP", lecture4.get("title"));

        JsonObject resources4 = lecture4.getJsonObject("resources");
        JsonArray books4 = resources4.getJsonArray("books");
        assertEquals(1, books4.size());

        JsonObject book4 = books4.getJsonObject(0);
        assertEquals("Bob White", book4.get("author"));
        assertEquals("Tech World", book4.getJsonObject("publisher").get("name"));
        assertEquals("Australia", book4.getJsonObject("publisher").get("location"));
        assertEquals("+6677889900", book4.getJsonObject("publisher").getJsonObject("contacts").get("phone"));
        assertEquals("info@techworld.au", book4.getJsonObject("publisher").getJsonObject("contacts").get("email"));
    }



    @Test
    public void testFromJSON1() throws Exception {
        String json = "{\"a\": 1, \"b\": \"b\", \"c\": {\"a\": 1, \"b\": null, \"d\": [0.1, \"a\", 1,2, 123, 1.23e+10, true, false, null]}}";
        JSONParser jsonParser = new JSONParser();
        JsonObject jsonObject = (JsonObject) jsonParser.fromJSON(json);

        assertEquals(1, jsonObject.get("a"));
        assertEquals("b", jsonObject.get("b"));

        JsonObject c = jsonObject.getJsonObject("c");
        assertNull(c.get("b"));

        JsonArray d = c.getJsonArray("d");
        assertEquals(0.1, d.get(0));
        assertEquals("a", d.get(1));
        assertEquals(123, d.get(4));
        assertEquals(1.23e+10, d.get(5));
        assertEquals(true, d.get(6));
        assertEquals(false, d.get(7));
        assertNull(d.get(8));
    }

    @Test
    public void testFromJSON2() throws Exception {
        String json = "[[1,2,3,\"abc\"]]";
        JSONParser jsonParser = new JSONParser();
        JsonArray jsonArray = (JsonArray) jsonParser.fromJSON(json);
        assertEquals(1, jsonArray.size());
        JsonArray innerArray = jsonArray.getJsonArray(0);
        assertEquals(1, innerArray.get(0));
        assertEquals(2, innerArray.get(1));
        assertEquals(3, innerArray.get(2));
        assertEquals("abc", innerArray.get(3));
    }

    @Test
    public void testBeautifyJSON() throws Exception {
        String json = "{\"name\": \"Detective Dee\", \"type\": \"Archer\", \"ability\":[\"Chasing Criminals\",\"Escape\",\"Secret Orders\"],\"history\":{\"DOB\":630,\"DOD\":700,\"position\":\"Prime Minister\",\"dynasty\":\"Tang Dynasty\"}}";
        System.out.println("Original JSON string:");
        System.out.println(json);
        System.out.println("\n");
        System.out.println("Beautified JSON string:");
        JSONParser jsonParser = new JSONParser();
        JsonObject drj = (JsonObject) jsonParser.fromJSON(json);
        System.out.println(drj);
    }
}
