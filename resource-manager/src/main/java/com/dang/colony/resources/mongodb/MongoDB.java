package com.dang.colony.resources.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import org.bson.types.BasicBSONList;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by dangqihe on 2017/3/17.
 */
public class MongoDB {
    private static Logger logger = LoggerFactory.getLogger(MongoDB.class);

    public static MongoCollection<Document> getCollection(String collectionName) throws UnknownHostException {
        return  MongoManagerFactory.getInstance().getDatabase().getCollection(collectionName);
    }

    public static Set<String> indexSet = new HashSet<String>();
    /**
     * 查询单个,按主键查询
     */
    public static DBObject find( String collectionName,String queryId,String value) {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        if("_id".equals(queryId))
            map.put(queryId, new ObjectId(value));
        else
            map.put(queryId, value);
        DBObject result;
        try {
             result = findOne(map, collectionName);
        }catch (UnknownHostException e){
            return null;
        }
        logger.debug("findById:"+collectionName+"<<"+queryId+"="+value +"|time:"+(System.currentTimeMillis()-start));
        return result;
    }

    /**
     * 查询单个
     * <br>------------------------------<br>
     * @param map
     * @param collectionName
     * @throws UnknownHostException
     */
    public static BasicDBObject findOne(Map<String, Object> map, String collectionName) throws UnknownHostException {
        BasicDBObject dbObject = getMapped(map);
        long start = System.currentTimeMillis();
        FindIterable<Document> result = getCollection(collectionName).find(dbObject);
        logger.debug("findOne:"+collectionName+"|map"+map.toString()+"|time:"+(System.currentTimeMillis()-start));
        if(result==null){
            return null;
        }
        for (Document document :result){
            return convertDBObject(document);
        }
        return null;
    }
    public static BasicDBObject convertDBObject(Map<String ,Object> map){
        BasicDBObject dbObject = new BasicDBObject();
        for(Map.Entry<String , Object> entry : map.entrySet()){
            dbObject.put(entry.getKey(),entry.getValue());
        }
        return dbObject;
    }
    public static Document convertDocument(Map<String ,Object> map){
        Document document = new Document();
        for(Map.Entry<String , Object> entry : map.entrySet()){
            document.put(entry.getKey(),entry.getValue());
        }
        return document;
    }
    public static List<Document> convertDocuments(List<DBObject> list){
        List<Document> result = new ArrayList<>();
        for(DBObject dbObject :list) {
            Document document = new Document();
            for(String key :dbObject.keySet()){
                document.put(key, dbObject.get(key));
            }
            result.add(document);
        }
        return  result;
    }


    /**
     *
     * <br>------------------------------<br>
     * @param map
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static BasicDBObject getMapped(Map<String, Object> map) {
        BasicDBObject dbObject = new BasicDBObject();
        Iterator<Map.Entry<String, Object>> iterable = map.entrySet().iterator();
        while (iterable.hasNext()) {
            Map.Entry<String, Object> entry = iterable.next();
            Object value = entry.getValue();
            String key = entry.getKey();
            if (key.startsWith("$") && value instanceof Map) {
                BasicBSONList basicBSONList = new BasicBSONList();
                Map<String, Object> conditionsMap = ((Map)value);
                Set<String> keys = conditionsMap.keySet();
                for (String k : keys) {
                    Object conditionsValue = conditionsMap.get(k);
                    if (conditionsValue instanceof Collection) {
                        conditionsValue =  convertArray(conditionsValue);
                    }
                    DBObject dbObject2 = new BasicDBObject(k, conditionsValue);
                    basicBSONList.add(dbObject2);
                }
                value  = basicBSONList;
            } else if (value instanceof Collection) {
                value =  convertArray(value);
            } else if (!key.startsWith("$") && value instanceof Map) {
                value = getMapped(((Map)value));
            }
            dbObject.put(key, value);
        }
        return dbObject;
    }

    @SuppressWarnings("rawtypes")
    private static Object[] convertArray(Object value) {
        Object[] values = ((Collection)value).toArray();
        return values;
    }

    public static void insert(Document document, String collectionName) throws UnknownHostException{
        long start = System.currentTimeMillis();
        getCollection(collectionName).insertOne(document);
        logger.debug("insert:"+collectionName+"|size:"+document.size()+"|time:"+(System.currentTimeMillis()-start));
    }

    public static void insert( List<Document> documentList,String collectionName) throws UnknownHostException {
        long start = System.currentTimeMillis();
        if(documentList==null||documentList.size()==0){
            return;
        }
        getCollection(collectionName).insertMany(documentList);
        logger.debug("insertList:"+collectionName+"|listSize:"+documentList.size()+"|time:"+(System.currentTimeMillis()-start));
    }


    public static void updateObject(String collectionName,String key ,String uId,String status)throws UnknownHostException {
        long start = System.currentTimeMillis();
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("status", status));
        BasicDBObject searchQuery = new BasicDBObject().append(key, uId);
        getCollection(collectionName).updateMany(searchQuery, newDocument);
        logger.debug("updateObject:"+collectionName+"|status:"+status+"|time:"+(System.currentTimeMillis()-start));
    }

    /**
     * @param collectionName
     * @param queryId
     * @param groupUniqueId
     * @param dbOld
     * @param dBObjectNew
     * @throws UnknownHostException
     */
    public static void appendObject(String collectionName,String queryId,String groupUniqueId,DBObject dbOld,DBObject dBObjectNew)throws UnknownHostException {

        Document document = new Document();
        document.putAll(dbOld.toMap());
        document.putAll(dBObjectNew.toMap());
        BasicDBObject searchQuery = new BasicDBObject().append(queryId, groupUniqueId);
        long start = System.currentTimeMillis();
        getCollection(collectionName).replaceOne(searchQuery, document);
        logger.debug("appendObject:"+collectionName+"|time:"+(System.currentTimeMillis()-start));
    }

    public static void ensureIndex(String name,String collectionName,String parserDetailId,boolean unique) throws UnknownHostException {
        if(indexSet.contains(name+collectionName+parserDetailId)){
            return;
        }else {
            indexSet.add(name+collectionName+parserDetailId);
        }
        if(haveIndex(collectionName,name)){
            return;
        }
        //创建索引
        BasicDBObject basicDb = new BasicDBObject();
        basicDb.put(parserDetailId, 1);
        IndexOptions indexOptions = new IndexOptions();
        indexOptions.background(true);
        indexOptions.unique(unique);
        indexOptions.name(name);
        getCollection(collectionName).createIndex(basicDb, indexOptions);
    }
    public static boolean haveIndex(String collectionName ,String name) throws UnknownHostException {
        ListIndexesIterable<Document> indexesIterable = getCollection(collectionName).listIndexes();
        try {
            for(Document document : indexesIterable){
                if( document.get("name").equals(name)){
                    return true;//如果有同名索引  不再建索引
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void ensureIndex(String name,String collectionName,BasicDBObject dbObject,boolean unique) throws UnknownHostException {

        if(indexSet.contains(name+collectionName)){
            return;
        }else {
            indexSet.add(name+collectionName);
        }
        if(haveIndex(collectionName,name)){
            return;
        }
        //创建索引
        IndexOptions indexOptions = new IndexOptions();
        indexOptions.background(true);
        indexOptions.unique(unique);
        indexOptions.name(name);
        getCollection(collectionName).createIndex(dbObject, indexOptions);
    }


    /**
     * 建指定类型分片 执行前必须给字段建相应索引
     * @param collectionName
     * @param fieldName
     * @param type
     * @return
     * @throws UnknownHostException
     */
    public static String shardKey(String collectionName, String fieldName, int type) throws UnknownHostException {
        //CommandResult cResult = getCollection(collectionName).getStats();
        //db.runCommand( { shardcollection : "crawler.eloanblack_7jubao_info",key : {_id: 1}} )
        String resultStr = "";
//		try {
//			if(getCollection(collectionName).findOne() != null){
//                return resultStr;
//            }
//			Boolean ifSharded = (Boolean)getCollection(collectionName).getStats().get("sharded");
//			if(ifSharded != null && !ifSharded){
//                String dbName = MongoManager.getInstance().getRealDbName();
//                Document document = new Document();
//                document.put("shardcollection", dbName + "." + collectionName);
//                document.put("key", new Document(fieldName, type));
//                Document result = MongoManager.getInstance().getAdminDatabase().runCommand(document);
//                resultStr = result.toJson();
//            }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        return resultStr;
    }

    /**
     * 默认建hashed分片 执行之前必须先给字段建hashed索引
     * @param collectionName
     * @param fieldName
     * @return
     * @throws UnknownHostException
     */
    public static String shardKey(String collectionName, String fieldName) throws UnknownHostException {
        String resultStr = "";
//		try {
//			if(getCollection(collectionName).findOne() != null){
//                return resultStr;
//            }
//			Boolean ifSharded = (Boolean)getCollection(collectionName).getStats().get("sharded");
//			if(ifSharded != null && !ifSharded){
//                String dbName = MongoManager.getInstance().getRealDbName();
//                Document document = new Document();
//                document.put("shardcollection", dbName + "." + collectionName);
//                document.put("key", new Document(fieldName, "hashed"));
//                Document result = MongoManager.getInstance().getAdminDatabase().runCommand(document);
//                resultStr = result.toJson();
//            }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        return resultStr;
    }

    public static void removeObject(String collectionName, String uId) throws UnknownHostException {
        BasicDBObject searchQuery = new BasicDBObject("uId", uId);
        getCollection(collectionName).deleteMany(searchQuery);
    }

    public static void removeObject(String collectionName, BasicDBObject dbObject) throws UnknownHostException {
        getCollection(collectionName).deleteMany(dbObject);
    }

    public static void main(String[] args) throws UnknownHostException {
        Document map = new Document();
        map.put("name","test1");
         insert(map, "test");
       // System.out.println(a.get("name"));
//        Mongo mg = new Mongo("localhost",27017);
//        DB db = mg.getDB("dangDB");
//        for(String s:db.getCollectionNames()){
//            System.out.println(s);
//        }
    }
}
