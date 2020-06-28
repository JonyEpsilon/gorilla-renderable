# Picasso [![GitHub Actions status |pink-gorilla/picasso](https://github.com/pink-gorilla/picasso/workflows/CI/badge.svg)](https://github.com/pink-gorilla/picasso/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/picasso.svg)](https://clojars.org/org.pinkgorilla/picasso)

- This project is used in [Notebook](https://github.com/pink-gorilla/gorilla-notebook) ,
  but the library can be used independently.
- Data structures from clojure/clojurescript have to be converted to some kind
of visual repesentation so that the notebook can render them
- For all clojure/clojurescript datatypes default renderers are defined here
- Custom data-types can implement the renderable protocol 
- The notebook receives the render-datastructure and renders it to the notebook cell.


This are the core concepts in picasso:

###  render (used in nrepl middleware)
```
(defprotocol Renderable
  (render [self]))
```

### paint (used in notebook-ui)
```
(defmulti paint :type)
```



Clojure/Clojurescript Data => (render) => Picasso DataStructure => (paint) => Notebook


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

## paint implementation
- `:default` picasso
- `:hiccup`  picasso
- `:html`  notebook-ui (needs gorilla-ui)
- `:text`  notebook-ui (needs gorilla-ui)
- `:pinkie` notebook-ui (needs pinkie)
- `:reagent` notebook-ui (needs pinkie)
- `:goldly` not yet implemented. will be in goldly 


## Licence

This code is licensed to you under the MIT licence. See LICENCE.txt for details.
