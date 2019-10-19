(ns pinkgorilla.ui.vega
  "plugin to render vega-charts in pink-gorilla"
  (:require 
   [pinkgorilla.ui.gorilla-renderable :refer :all] ; define Renderable (which has render function)
   [hiccup.core :as hiccup]
 ;  [clojure.data.json :as json]
   ))


;; UI Modules use RequireJS.
;; Require-JS configuration has to be done centrally.

;; The follwing RequireJS modules are defined in PinkGorilla Notebook
;; 'vega-embed'
;; 'vega-lib'
;; 'vega-lite'
;; 'vega'

(def module-test "
  define([], function () {
      return {
         render: function (id, data) {
            console.log ('vega-module-test is rendering to id: ' + id);
            var dataJson = JSON.stringify(data)
            console.log ('vega-module-test data: ' + dataJson);
            var domElement = document.getElementById (id); 
            var p = document.createElement ('p');
            var json = JSON.stringify (data);
            var textnode = document.createTextNode (dataJson);  
            //var textnode = document.createTextNode ('vega rocks');  
            p.appendChild (textnode);
            domElement.appendChild (p);
         }
      }
  });
")


(def module "
  define([], function () {
      return {
         render: function (id, data) {
            console.log ('vega-module is rendering to id: ' + id);
            var dataJson = JSON.stringify(data)
            console.log ('vega-module-test data: ' + dataJson);
            require(['vega-embed'], function(vegaEmbed) {
              let spec = data;
              let id_selector = '#' + id;
              vegaEmbed(id_selector, spec, {defaultStyle:true}).catch(console.warn);
              }, function(err) {
                console.log('Failed to load');
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
  
  
  (js-name (vega! {:bongo 1}))
  
  )


