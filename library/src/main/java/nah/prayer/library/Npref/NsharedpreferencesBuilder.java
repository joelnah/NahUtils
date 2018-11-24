package nah.prayer.library.Npref;

import android.content.Context;

import com.google.gson.Gson;

public class NsharedpreferencesBuilder {

  /**
   * NEVER ever change STORAGE_TAG_DO_NOT_CHANGE and TAG_INFO.
   * It will break backward compatibility in terms of keeping previous data
   */
  private static final String STORAGE_TAG_DO_NOT_CHANGE = "Hawk2";

  private Context context;
  private Storage cryptoStorage;
  private Converter converter;
  private Parser parser;
  private Encryption encryption;
  private Serializer serializer;
  private LogInterceptor logInterceptor;

  public NsharedpreferencesBuilder(Context context) {
    NsharedpreferencesUtils.checkNull("Context", context);

    this.context = context.getApplicationContext();
  }

  public NsharedpreferencesBuilder setStorage(Storage storage) {
    this.cryptoStorage = storage;
    return this;
  }

  public NsharedpreferencesBuilder setParser(Parser parser) {
    this.parser = parser;
    return this;
  }

  public NsharedpreferencesBuilder setSerializer(Serializer serializer) {
    this.serializer = serializer;
    return this;
  }

  public NsharedpreferencesBuilder setLogInterceptor(LogInterceptor logInterceptor) {
    this.logInterceptor = logInterceptor;
    return this;
  }

  public NsharedpreferencesBuilder setConverter(Converter converter) {
    this.converter = converter;
    return this;
  }

  public NsharedpreferencesBuilder setEncryption(Encryption encryption) {
    this.encryption = encryption;
    return this;
  }

  LogInterceptor getLogInterceptor() {
    if (logInterceptor == null) {
      logInterceptor = new LogInterceptor() {
        @Override public void onLog(String message) {
          //empty implementation
        }
      };
    }
    return logInterceptor;
  }

  Storage getStorage() {
    if (cryptoStorage == null) {
      cryptoStorage = new SharedPreferencesStorage(context, STORAGE_TAG_DO_NOT_CHANGE);
    }
    return cryptoStorage;
  }

  Converter getConverter() {
    if (converter == null) {
      converter = new NsharedpreferencesConverter(getParser());
    }
    return converter;
  }

  Parser getParser() {
    if (parser == null) {
      parser = new GsonParser(new Gson());
    }
    return parser;
  }

  Encryption getEncryption() {
    if (encryption == null) {
      encryption = new ConcealEncryption(context);
      if (!encryption.init()) {
        encryption = new NoEncryption();
      }
    }
    return encryption;
  }

  Serializer getSerializer() {
    if (serializer == null) {
      serializer = new NsharedpreferencesSerializer(getLogInterceptor());
    }
    return serializer;
  }

  public void build() {
    Npref.build(this);
  }
}
