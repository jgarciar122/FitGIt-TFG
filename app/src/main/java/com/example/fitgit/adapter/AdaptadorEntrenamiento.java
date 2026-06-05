package com.example.fitgit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitgit.R;
import com.example.fitgit.model.Ejercicio;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptadorEntrenamiento extends RecyclerView.Adapter<AdaptadorEntrenamiento.EjercicioViewHolder> {

    private List<Ejercicio> listaEjercicios = new ArrayList<>();

    // mapa que relaciona cada ejercicio con sus series (kg y reps)
    private Map<String, List<AdaptadorSerie.FilaSerie>> seriesPorEjercicio = new HashMap<>();

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.listaEjercicios = ejercicios;
        // pongo una serie vacía de inicio para que el usuario vea algo al abrir el entrenamiento
        for (Ejercicio e : ejercicios) {
            if (!seriesPorEjercicio.containsKey(e.getId())) {
                List<AdaptadorSerie.FilaSerie> series = new ArrayList<>();
                series.add(new AdaptadorSerie.FilaSerie(0, 0));
                seriesPorEjercicio.put(e.getId(), series);
            }
        }
        notifyDataSetChanged();
    }

    // devuelve las series al finalizar el entrenamiento para guardarlas en la BD
    public Map<String, List<AdaptadorSerie.FilaSerie>> getSeriesPorEjercicio() {
        return seriesPorEjercicio;
    }

    @NonNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ejercicio_entrenamiento, parent, false);
        return new EjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        Ejercicio ejercicio = listaEjercicios.get(position);
        holder.tvNombre.setText(ejercicio.getNombreMostrar());

        List<AdaptadorSerie.FilaSerie> series = seriesPorEjercicio.get(ejercicio.getId());

        // creo un adaptador de series para cada ejercicio
        AdaptadorSerie adaptadorSeries = new AdaptadorSerie();
        if (series != null) {
            for (AdaptadorSerie.FilaSerie fila : series) {
                adaptadorSeries.añadirSerieConDatos(fila.kg, fila.reps);
            }
        }

        holder.rvSeries.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvSeries.setAdapter(adaptadorSeries);

        holder.btnAnadirSerie.setOnClickListener(v -> {
            adaptadorSeries.añadirSerie();
            seriesPorEjercicio.put(ejercicio.getId(), adaptadorSeries.getSeries());
        });

        // guardo la referencia en el mapa para recuperarla al finalizar
        seriesPorEjercicio.put(ejercicio.getId(), adaptadorSeries.getSeries());
    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    static class EjercicioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        RecyclerView rvSeries;
        MaterialButton btnAnadirSerie;

        public EjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_ejercicio_entrenamiento);
            rvSeries = itemView.findViewById(R.id.rv_series_entrenamiento);
            btnAnadirSerie = itemView.findViewById(R.id.btn_anadir_serie_entrenamiento);
        }
    }
}