package nah.prayer.library.Nsharedpreferences;

public class DefaultNsharedpreferencesFacade implements NsharedpreferencesFacade {

  private final Storage storage;
  private final Converter converter;
  private final Encryption encryption;
  private final Serializer serializer;
  private final LogInterceptor logInterceptor;

  public DefaultNsharedpreferencesFacade(NsharedpreferencesBuilder builder) {
    encryption = builder.getEncryption();
    storage = builder.getStorage();
    converter = builder.getConverter();
    serializer = builder.getSerializer();
    logInterceptor = builder.getLogInterceptor();

    logInterceptor.onLog("Npref.init -> Encryption : " + encryption.getClass().getSimpleName());
  }

  @Override public <T> boolean put(String key, T value) {
    // Validate
    NsharedpreferencesUtils.checkNull("Key", key);
    log("Npref.put -> key: " + key + ", value: " + value);

    // If the value is null, delete it
    if (value == null) {
      log("Npref.put -> Value is null. Any existing value will be deleted with the given key");
      return delete(key);
    }

    // 1. Convert to text
    String plainText = converter.toString(value);
    log("Npref.put -> Converted to " + plainText);
    if (plainText == null) {
      log("Npref.put -> Converter failed");
      return false;
    }

    // 2. Encrypt the text
    String cipherText = null;
    try {
      cipherText = encryption.encrypt(key, plainText);
      log("Npref.put -> Encrypted to " + cipherText);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (cipherText == null) {
      log("Npref.put -> Encryption failed");
      return false;
    }

    // 3. Serialize the given object along with the cipher text
    String serializedText = serializer.serialize(cipherText, value);
    log("Npref.put -> Serialized to " + serializedText);
    if (serializedText == null) {
      log("Npref.put -> Serialization failed");
      return false;
    }

    // 4. Save to the storage
    if (storage.put(key, serializedText)) {
      log("Npref.put -> Stored successfully");
      return true;
    } else {
      log("Npref.put -> Store operation failed");
      return false;
    }
  }

  @Override public <T> T get(String key) {
    log("Npref.get -> key: " + key);
    if (key == null) {
      log("Npref.get -> null key, returning null value ");
      return null;
    }

    // 1. Get serialized text from the storage
    String serializedText = storage.get(key);
    log("Npref.get -> Fetched from storage : " + serializedText);
    if (serializedText == null) {
      log("Npref.get -> Fetching from storage failed");
      return null;
    }

    // 2. Deserialize
    DataInfo dataInfo = serializer.deserialize(serializedText);
    log("Npref.get -> Deserialized");
    if (dataInfo == null) {
      log("Npref.get -> Deserialization failed");
      return null;
    }

    // 3. Decrypt
    String plainText = null;
    try {
      plainText = encryption.decrypt(key, dataInfo.cipherText);
      log("Npref.get -> Decrypted to : " + plainText);
    } catch (Exception e) {
      log("Npref.get -> Decrypt failed: " + e.getMessage());
    }
    if (plainText == null) {
      log("Npref.get -> Decrypt failed");
      return null;
    }

    // 4. Convert the text to original data along with original type
    T result = null;
    try {
      result = converter.fromString(plainText, dataInfo);
      log("Npref.get -> Converted to : " + result);
    } catch (Exception e) {
      log("Npref.get -> Converter failed");
    }

    return result;
  }

  @Override public <T> T get(String key, T defaultValue) {
    T t = get(key);
    if (t == null) return defaultValue;
    return t;
  }

  @Override public long count() {
    return storage.count();
  }

  @Override public boolean deleteAll() {
    return storage.deleteAll();
  }

  @Override public boolean delete(String key) {
    return storage.delete(key);
  }

  @Override public boolean contains(String key) {
    return storage.contains(key);
  }

  @Override public boolean isBuilt() {
    return true;
  }

  @Override public void destroy() {
  }

  private void log(String message) {
    logInterceptor.onLog(message);
  }
}
