# Basketball Countdown Timer
![Workflow result](https://github.com/bruno125/compose-challenge-week-2/workflows/Check/badge.svg)

## :scroll: Description
This app allows you to manage the timers for a basketball game, which has:
- a 24-seconds shot clock for each play
- a 12-minute time clock for each quarter.

## :bulb: Motivation and Context

Motivation:
My very first Android app 8 years ago was a (much more simple) basketball timer as well.
I'm happy to see not only my progress over the years, but also Android's with Compose.

Features:
- :art: Canvas API: the digital board is drawn manually using Compose's powerful `Canvas` API.
- :clapper: States: redraws are triggered by state changes.
- :rocket: Animations: color changes, bounce & shake animation are done using `Animation` APIs.

Additionally, it's pretty cool how easy it is to interact with the Canvas in Compose. Many years ago
I did a talk ([Android Custom Views](https://speakerdeck.com/bruno125/android-custom-views)) in
which I talked about how to create a very similar clock implementation. Compose makes it much
simpler, no need to deal with `onDraw`, `onMeasure`, `onLayout`, `resolvedSize`, etc.!


## :camera_flash: Screenshots
<!-- You can add more screenshots here if you like -->
<img src="/results/screenshot_1.png" width="260">&emsp;<img src="/results/screenshot_2.png" width="260">

## License
```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```