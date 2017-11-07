[![Build Status](https://circleci.com/gh/ubiratansoares/uol-challenge.svg?style=shield&circle-token=751be712397ecf47211bbcb2fafbd0286e7620fc)](https://circleci.com/gh/ubiratansoares/uol-challenge)

# UOL Challenge
 
> This is a practical project for a Software Engineer position.
> Please check details below.

## Why

Because this position should be mine ... 😹😹😹

## How

This project leverages on several modern approaches to build Android applications :

- 100% written in Kotlin programming language
- Massive test coverage, including tests at unit and integration levels, using both JVM-local and instrumentation environments 
- Continuous integration (and ready-to-go Continuous Delivery)
- Modularization-ready
- MVVM Architecture powered by Android Architecture Components
- Rock-solid reactive data flows leveraging RxJava2

This project is focused on `correctness` of the content experience, both for news feed 
feature and news content screen. 

As a 100% online application, this demo project implements several strategies to deal with most common error 
cases incoming from the Web such and 404, 5xx, 4XY, networking headaches and so on. 
These strategies are applied both for news feed (REST API) and news content (WebView), 
leveraging decoupled presentation behaviors and inversion of control (to data sources)

Note that you must build against the `live` build variant in order to let the app perform real calls to the REST API 

You may check some [screenshots](https://github.com/ubiratansoares/uol-challenge/tree/master/extras) 
from the resulting app at gallery

## When (Development Tracking)

Histories and tasks for every single feature of this project were tracked 
at this [Trello Board](https://trello.com/b/ghpAQrPc/uol-challenge)


## License

```
The MIT License (MIT)

Copyright (c) 2017 Ubiratan Soares

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.