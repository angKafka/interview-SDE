package database;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import util.LocalDBOperations;
import util.PathFinder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UserDB<T> implements LocalDBOperations<T> {
    private List<T> list;
    private final ObjectMapper mapper;
    private final Class<T> type;

    public UserDB(Class<T> type) {
        this.type = type;
        this.mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.list = loadDataFromLocalDB(); // Load data on initialization
    }

    @Override
    public List<T> loadDataFromLocalDB() {
        try {
            File userFromDB = new File(PathFinder.USER_DB_LOCATION);
            if (userFromDB.exists()) {
                JavaType collectionType = mapper.getTypeFactory()
                        .constructCollectionType(List.class, type);
                list = mapper.readValue(userFromDB, collectionType);
            } else {
                list = Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public void saveDataToLocalDB() {
        try {
            File userToDB = new File(PathFinder.USER_DB_LOCATION);
            mapper.writeValue(userToDB, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> getAll() {
        return list;
    }

    public void setAll(List<T> data) {
        this.list = data;
    }
}