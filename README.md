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

<a href="https://play.google.com/store/apps/details?id=com.mauriciotogneri.tpgwear" target="_blank">
	<img src="https://play.google.com/intl/en_us/badges/images/apps/en-play-badge.png" align="left" height="72" >
</a>