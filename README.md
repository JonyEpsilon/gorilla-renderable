# Picasso [![GitHub Actions status |pink-gorilla/gorilla-renderable](https://github.com/pink-gorilla/gorilla-renderable/workflows/CI/badge.svg)](https://github.com/pink-gorilla/gorilla-renderable/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/gorilla-renderable.svg)](https://clojars.org/org.pinkgorilla/gorilla-renderable)

- This project is used in [Notebook](https://github.com/pink-gorilla/gorilla-notebook) ,
  but the library can be used independently.
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


# Clojure/Clojurescript type based rendering

- All clj/cljs datatypes have default renderers that will be selected based on 
  the type of the data.

## Syntactic sugar for librarys that need visualization

- Goal is to use meta-data to trigger rendering of function output.
- Say we want a function that creates a vega spec to be rendered with a vega renderer:

```
(defn timeseries-plot 
  "timeseries-plot creates vega spec for a timeseries plot"
  [data] 
  ^{:render-with :p/vega} vega-spec)
```

- The user would then use this with:

```
[:div [:h1 "demo"]
      (timeseries-plot data)]
```

- the rendering system would then internally convert this to:

```
[:div [:h1 "demo"]
      [:p/vega (timeseries-plot data)]]
```

- This saves the library author from writing a wrapper.


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
