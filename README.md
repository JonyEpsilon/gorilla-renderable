# Pink Gorilla Renderable [![GitHub Actions status |pink-gorilla/gorilla-renderable](https://github.com/pink-gorilla/gorilla-renderable/workflows/CI/badge.svg)](https://github.com/pink-gorilla/gorilla-renderable/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/gorilla-renderable.svg)](https://clojars.org/org.pinkgorilla/gorilla-renderable)

- This project is part of [PinkGorilla Notebook](https://github.com/pink-gorilla/gorilla-notebook)
- Data structures from clojure/clojurescript have to be converted to some kind
of visual repesentation so that the notebook can render them
- For all clojure/clojurescript datatypes default renderers are defined here
- Custom data-types can implement the renderable protocol 
- The notebook receives the render-datastructure and renders it to the notebook cell.

```
(defprotocol Renderable
  (render [self]))
```

Clojure/Clojurescript Data => (render) => Renderable DataStructure => (render) => Notebook


## Unit Tests 

Clojure:
```
lein test
```

Clojurescript:
```
npm install
lein test-js
```

## Licence

This code is licensed to you under the MIT licence. See LICENCE.txt for details.
