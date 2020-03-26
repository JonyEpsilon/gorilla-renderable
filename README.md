# Pink Gorilla Renderable [![GitHub Actions status |pink-gorilla/gorilla-renderable](https://github.com/pink-gorilla/gorilla-renderable/workflows/CI/badge.svg)](https://github.com/pink-gorilla/gorilla-renderable/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/gorilla-renderable.svg)](https://clojars.org/org.pinkgorilla/gorilla-renderable)

- This project is part of [PinkGorilla Notebook](https://github.com/pink-gorilla/gorilla-notebook)
- Data structures from clojure/clojurescript have to be converted to some kind
of visual repesentation so that the notebook can render them
- For all clojure/clojurescript datatypes default renderers are defined here
- Custom data-types can implement the renderable protocol 
- The notebook receves the render-datastructure and renders it to the notebook cell.

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


## History

- This project origianlly comes from https://github.com/JonyEpsilon/gorilla-renderable
- It is forked to support new features in pink-gorilla.


## Licence

This code is licensed to you under the MIT licence. See LICENCE.txt for details.
