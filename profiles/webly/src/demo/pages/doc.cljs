(ns demo.pages.doc
  (:require
   [re-frame.core :as rf]
   [webly.web.handler :refer [reagent-page]]
   [picasso.document.viewer :refer [doc-view doc-view-id]]
   [picasso.data.document :as data]
   [picasso.kernel.picasso :refer [picasso-result]]
   [picasso.document.transactor] ;side-effects
   ; ui
   [ui.markdown.goldly.core] ;side-effects
   [ui.markdown.viewer :refer [markdown-viewer]]
   [ui.code.goldly.core] ;side-effects
   [ui.code.highlight.viewer :refer [code-viewer]]
   ))

(defn link-fn [fun text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:on-click fun} text])

(defn link-dispatch [rf-evt text]
  (link-fn #(rf/dispatch rf-evt) text))

(defn link-href [href text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:href href} text])

(defn block [& children]
  (into [:div.bg-blue-400.m-5.inline-block {:class "w-1/4"}]
        children))


(defn md-view [id md state]
  [:div.bg-blue-500
   [markdown-viewer md]])

(defn code-view [id {:keys [kernel code]} {:keys [picasso] :as state}]
  [:div
   ;[:div.bg-green-500 
    [code-viewer code]
    ;]
   [:div.bg-yellow-400
    (if picasso
      [picasso-result picasso]
      [:p "xxx:" (pr-str state)])]])

(rf/dispatch [:doc/add data/document])

(def opts
  {:md-view md-view :code-view code-view})

(defmethod reagent-page :demo/doc [& args]
  [:div
   [:p [link-dispatch [:bidi/goto  :demo/main] "main"]]
   [:h1 "picasso document"]

   [:p [link-dispatch [:doc/exec [:clear-all]] "clear all"]]
   [:p [link-dispatch [:doc/exec [:eval-all]] "eval all"]]

   [block
    [:p.text-4xl "doc"]
    [:p "edn eval"]

    ;[doc-view opts data/document]
    [doc-view-id opts (:id data/document)]]])
