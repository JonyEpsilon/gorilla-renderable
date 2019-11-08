# Pinkgorilla renderable

- this project is part of [PinkGorilla Notebook](https://github.com/pink-gorilla/gorilla-notebook)
- data structures from clojure/clojurescript have to be converted to some kind
of visual repesentation so that the notebook can render them
- for all clojure/clojurescript datatypes default renderers are defined here
- custom data-types can implement the renderable protocol 
- the notebook receves the render-datastructure and renders it to the notebook cell.

```
(defprotocol Renderable
  (render [self]))
```

Clojure/Clojurescript Data => (render) => Renderable DataStructure => (render) => Notebook



## Unit Tests 

Clojure:
```
NOT IMPLEMENTED
```

Clojurescript:
```
lein doo
```


## History

- This project origianlly comes from https://github.com/JonyEpsilon/gorilla-renderable
- It is forked to support new features in pink-gorilla.


## Licence

This code is licensed to you under the MIT licence. See LICENCE.txt for details.
