(ns unit-conversion.boot-build)

(def conversion-map
  {:mass {:gram 1.0, :pound 0.00220462 :ounce 0.035274}
   :length {:meter 1.0 :foot 3.28084}
   :area {:meter-squared 1.0 :acre 0.000247105}
   :volume {:meter-cubed 1.0 :liter 1000.0}})

(defn type-to-kwd [input output]
  (keyword (str (name input) "-to-" (name output))))

(def kind-map (reduce into #{} (for [[_ m] conversion-map] (keys m))))

(def ratio-map
  (into {}
        (for [[type m] conversion-map]
          (into {}
                (for [[k1 v1] m
                      [k2 v2] m]
                  [(type-to-kwd k1 k2) (-> 1 (/ v1) (* v2))])))))

(defn convert [value input output]
  (if-let [ratio (get ratio-map (type-to-kwd input output))]
    (* value ratio)
    (throw
     (Error.
      (if (and (contains? kind-map input) (contains? kind-map output))
        (str input " and " output " are different types of measurements")
        (str "I don't know how to convert " input " to " output))))))

;; floating point, precission, don't use at a reactor

(convert 1 :pound :ounce) ;; => 16
(convert 1 :pound :gram) ;; => 0.453592
(convert 1 :gram :meter) ;; => Error ":gram and :meter are different types of measurement"
(convert 1 :pound :cowhoof) ;; => Error "I don't know how to convert :pound to :cowhoof"
