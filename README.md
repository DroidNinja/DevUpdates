<p>
<img src="https://github.com/DroidNinja/DevUpdates/blob/master/art/banner.png?raw=true"/>
</p>

<p>
<img src="https://github.com/DroidNinja/DevUpdates/blob/master/art/light_theme.png?raw=true"/>

<img src="https://github.com/DroidNinja/DevUpdates/blob/master/art/dark_theme.png?raw=true"/>
</p>

DevUpdates
=======

An app that keeps you updated on Android Ecosystem from various different sources. Basically, news app for developer updates.

## Why it was built
I have always wanted to build a open source project that is useful for me and other people. At the same time, I wanted to learn
new things that have been happening in Android Development Ecosystem. So, instead of using third party apis to build a sample app, I chose 
to build this news app that helps me learn things from various sources.

It basically scrapes or uses rss feed to fetch content from different sources and saves it locally in the database. It is an offline app.

Note: This project is for learning purpose only. I will probably do different experiments, use cases that might not be suitable for ideal scenarios.
Although If you have some suggestions regarding data sources, I will try to add them. Otherwise, fork this repo, do your thing.

## Features

- Integrated multiple sources (focused on android only):
    - Home (aggregated feed from different sources)
    - Github Trending 
    - Android Weekly
    - Official Android Blog
    - Medium - Android Developers
    - Medium- ProAndroidDev
    - Styling Android
    - Commonsware
    - Google Developer Experts
    - Antonio Leiva
    - Chris Banes
- Pagination on supported services
- Quick Bookmarks  
- Night mode
- Drag/Drop order of services

## TODO
- Devik module - Make this development only module more usable


## Technologies

- Kotlin
- Coroutines  
- Retrofit 2/ Gson
- AndroidX/Jetpack
- Dagger 2 + Hilt
- Room
- Firebase

## Inspiration

- [Zac Sweers](https://twitter.com/ZacSweers) and his [CatchUp](https://github.com/ZacSweers/CatchUp)

## Development

Since this project is only for learning purposes. It uses experimental components probably which are in 
alpha/beta. To run this project, Install latest version of the canary channel of Android Studio.

# License
-------

	Copyright (C) 2021 Arun Sharma

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
