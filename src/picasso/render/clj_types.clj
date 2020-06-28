(ns picasso.render.clj-types
  "converts clojure values to html representation"
  (:require
   [picasso.protocols :refer [make Renderable render]]
   [picasso.render.list-like :refer [list-like]]))


;; helper functions

;; A lot of things render to an HTML span, with a class to mark the type of thing. 
;; This helper constructs the rendered
;; value in that case.


(defn- span-render
  [thing class]
  (make :hiccup
        [:span {:class class} (pr-str thing)]))

;; Renderers for basic Clojure forms **

;; A default, catch-all renderer that takes anything we don't know what to do with


(extend-type Object
  Renderable
  (render [self]
    (span-render self "clj-unknown")))

;; nil values are a distinct thing of their own
(extend-type nil
  Renderable
  (render [self]
    (span-render self "clj-nil")))

(extend-type Boolean
  Renderable
  (render [self]
    (span-render self "clj-boolean")))

(extend-type clojure.lang.Symbol
  Renderable
  (render [self]
    (span-render self "clj-symbol")))

(extend-type clojure.lang.Keyword
  Renderable
  (render [self]
    (span-render self "clj-keyword")))

(extend-type clojure.lang.Var
  Renderable
  (render [self]
    (span-render self "clj-var")))

(extend-type clojure.lang.Atom
  Renderable
  (render [self]
    (span-render self "clj-atom")))

(extend-type clojure.lang.Agent
  Renderable
  (render [self]
    (span-render self "clj-agent")))

(extend-type clojure.lang.Ref
  Renderable
  (render [self]
    (span-render self "clj-ref")))

(extend-type java.lang.String
  Renderable
  (render [self]
    (span-render self "clj-string")))

(extend-type java.lang.Character
  Renderable
  (render [self]
    (span-render self "clj-char")))

(extend-type java.lang.Long
  Renderable
  (render [self]
    (span-render self "clj-long")))

(extend-type java.lang.Double
  Renderable
  (render [self]
    (span-render self "clj-double")))

(extend-type clojure.lang.BigInt
  Renderable
  (render [self]
    (span-render self "clj-bigint")))

(extend-type java.math.BigDecimal
  Renderable
  (render [self]
    (span-render self "clj-bigdecimal")))

(extend-type clojure.lang.Ratio
  Renderable
  (render [self]
    (span-render self "clj-ratio")))

(extend-type java.lang.Class
  Renderable
  (render [self]
    (span-render self "clj-class")))

;; renderers for collection of items

(extend-type clojure.lang.IPersistentVector
  Renderable
  (render [self]
    (list-like {:class "clj-vector"
                :open "["
                :close "]"
                :separator " "}
               self)))

(extend-type clojure.lang.LazySeq
  Renderable
  (render [self]
    (list-like {:class "clj-lazy-seq"
                :open "("
                :close ")"
                :separator " "}
               self)))

(extend-type clojure.lang.IPersistentList
  Renderable
  (render [self]
    (list-like {:class "clj-list"
                :open "("
                :close ")"
                :separator " "}
               self)))

;; TODO: is this really necessary? Is there some interface I'm missing for lists? Or would just ISeq work?
(extend-type clojure.lang.ArraySeq
  Renderable
  (render [self]
    (list-like {:class "clj-list"
                :open "("
                :close ")"
                :separator " "}
               self)))

(extend-type clojure.lang.Cons
  Renderable
  (render [self]
    (list-like {:class "clj-list"
                :open "("
                :close ")"
                :separator " "}
               self)))


;; When we render a map we will map over its entries, which will yield key-value pairs represented as vectors. To render
;; the map we render each of these key-value pairs with this helper function. They are rendered as list-likes with no
;; bracketing. These will then be assembled in to a list-like for the whole map by the IPersistentMap render function.


(extend-type clojure.lang.IPersistentMap
  Renderable
  (render [self]
    (list-like {:class  "clj-map"
                :open "{"
                :close "}"
                :separator " "}
               self)))

(extend-type clojure.lang.IPersistentSet
  Renderable
  (render [self]
    (list-like {:class  "clj-set"
                :open "#{"
                :close "}"
                :separator " "}
               self)))

;; A record is like a map, but it is tagged with its type
(extend-type clojure.lang.IRecord
  Renderable
  (render [self]
    (list-like {:class  "clj-record"
                :open "{"
                :close "}"
                :separator " "
                :type (str "#" (pr-str (type self)))}
               self)))