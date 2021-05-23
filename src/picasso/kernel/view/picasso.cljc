(ns picasso.kernel.view.picasso
  (:require
   [picasso.protocols :refer [paint]]))

(defn picasso-result [picasso-spec]
  (let [r (if (map? picasso-spec)
            [:div.flex-grow-1.result-one (paint picasso-spec)] ; one spec
            (into [:div.flex-grow-1.flex.flex-col.result-many] ; .mt-5 .w-full.h-full.prose
                  (map (fn [s] (paint s)) picasso-spec))) ; multiple specs
        ]
    r))

#_(defn picasso-cell [picasso-spec]
    [:div
     [:p.bg-blue-500 "picasso-spec:" (pr-str picasso-spec)]
     [:p.bg-blue-500 "picasso-spec second:" (pr-str (second picasso-spec))]
     [picasso-cell-raw picasso-spec]])

