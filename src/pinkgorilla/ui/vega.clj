(ns pinkgorilla.ui.vega
  "plugin to render vega-charts in pink-gorilla"
  (:require 
   [pinkgorilla.ui.gorilla-renderable :refer :all] ; define Renderable (which has render function)
   [pinkgorilla.ui.module-test :refer [module-test]]
   [hiccup.core :as hiccup]
   ))


;; UI Modules use RequireJS.
;; Require-JS configuration has to be done centrally.

;; The follwing RequireJS modules are defined in PinkGorilla Notebook
;; 'vega-embed'
;; 'vega-lib'
;; 'vega-lite'
;; 'vega'

;; "https://vega.github.io/schema/vega/v5.json


;; UNUSED.
;(def vega-version "5.4.0")
;(def vega-lite-version "3.4.0")
;(def vega-embed-version "4.4.0")


;; NOTES:
;; Vega-Embed depends on Vega-Lite and Vega
;; Vega-Lite depends on Vega
;; The load order has to be VEGA, VEGA-LITE, VEGA-EMBED.

;; https://github.com/vega/vega-embed/issues/8
;; https://github.com/biocore/qurro/commit/baf8542bd08dfdb5a078bca3f73cddbd79faef93



(def module "
  define([], function () {
      return {
         version: 'vega 0.0.4',
         render: function (id_or_domel, data) {
            var selector_or_domel = id_or_domel;
            if (typeof id_or_domel === 'string' || id_or_domel instanceof String) {
               selector_or_domel = '#'+ id_or_domel;
               console.log ('vega-module is rendering to selector id: ' + selector_or_domel);
            } else {
               console.log ('vega-module is rendering to dom-element');
            }
            var dataJson = JSON.stringify(data)
            console.log ('vega-module data: ' + dataJson);
            require(['vega', 'vega-lite', 'vega-embed'], function(vega, vegaLite, vegaEmbed) {
              vegaEmbed(selector_or_domel, data, {defaultStyle:true}).catch(function(em) {
                  console.log('Error in Rendering Vega Spec: ' + em)
                 });
              }, function(err) {
                console.log('Vega-Embed failed to load');
            });
         }
      }
  });
")



(defn vega! [spec]
  "renders vega spec to a gorilla cell"
  (reify Renderable
    (render [_]
      {:type :jsscript
       :content 
         {:data spec
          ;:module module-test
          :module module
          }
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })
    
    ))


(comment
  
  
  (render (vega! {:bongo 1}))
  
  )


