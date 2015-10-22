# TPG Wear

The **TPG Wear** app allows you to quickly get live information about the next departures of a given stop. It comes with a companion mobile app where you can store your favorite stops that will be displayed in the main menu of the smartwatch app.

The application uses the official **TPG** (_Geneva Public Transport_) API as source of data.

# Configuration

After you clone the repository you have to add the following lines to the file `gradle.properties`:

```ini
RELEASE_KEY_ALIAS      = ???
RELEASE_KEY_PASSWORD   = ???
RELEASE_STORE_PASSWORD = ???
RELEASE_STORE_FILE     = ???
```

Add your **Application Key** to the file `mobile/src/main/res/xml/keys.xml`.

[![Get it on Google Play](https://developer.android.com/images/brand/en_generic_rgb_wo_60.png)](https://play.google.com/store/apps/details?id=com.mauriciotogneri.tpgwear)