package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachingProxyHandler<T> implements InvocationHandler {
    private final T targetObject;
    private final Map<Integer , Object> cache = new ConcurrentHashMap<>();

    public CachingProxyHandler(T targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("findById")) {
            Integer id = (Integer) args[0]; // Cast to Integer

            if (cache.containsKey(id)) {
                System.out.println("Cache hit for ID: " + id);
                return cache.get(id);
            }

            Object result = method.invoke(targetObject, args);
            if (result != null) {
                cache.put(id, result);
                System.out.println("Cache stored for ID: " + id);
            } else {
                System.out.println("No result found in DB for ID: " + id);
            }
            return result;
        }
        return method.invoke(targetObject, args);
    }
}
