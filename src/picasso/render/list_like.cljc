(ns picasso.render.list-like
  (:require
   [picasso.protocols :refer [make render]]))

(defn list-like-render [options entry & [items]]
  (let [items (or items entry)] ; mapping over a map gets [k v], all other seqs have only v
    (make :list-like
          (merge options
                 {:items (map render items) ; this calls a multi-method
                  :value (pr-str entry)}))))
