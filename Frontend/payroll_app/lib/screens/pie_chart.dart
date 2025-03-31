import 'package:flutter/material.dart';
import 'package:pie_chart/pie_chart.dart';

class ReusablePieChart extends StatelessWidget {
  final Map<String, double> dataMap; // Data for the chart
  final List<Color> colorList; // Colors for each section
  final String centerText; // Text to show in the center
  final double chartRadius; // Radius of the chart
  final Map<String, String> legendLabels; // Labels for the legend

  // Constructor with parameters for dynamic usage
  const ReusablePieChart({
    super.key,
    required this.dataMap,
    required this.colorList,
    required this.centerText,
    required this.chartRadius,
    required this.legendLabels,
  });

  @override
  Widget build(BuildContext context) {
    // Gradients for the pie chart sections
    final gradientList = colorList.map((color) {
      return [color.withOpacity(0.8), color.withOpacity(1.0)];
    }).toList();

    return PieChart(
      dataMap: dataMap,
      animationDuration: const Duration(milliseconds: 1000),
      chartRadius: chartRadius,
      colorList: colorList,
      initialAngleInDegree: 0,
      chartType: ChartType.ring,
      ringStrokeWidth: 50,
      centerText: centerText,
      centerTextStyle: const TextStyle(
        fontSize: 12,
        fontWeight: FontWeight.bold,
        color: Colors.black,
      ),
      legendOptions: const LegendOptions(
        showLegendsInRow: false,
        legendPosition: LegendPosition.right,
        showLegends: true,
        legendShape: BoxShape.rectangle,
        legendTextStyle: TextStyle(
          fontWeight: FontWeight.bold,
          fontSize: 14,
          color: Colors.black,
        ),
      ),
      chartValuesOptions: const ChartValuesOptions(
        showChartValueBackground: true,
        showChartValues: true,
        showChartValuesInPercentage: true,
        showChartValuesOutside: true,
        decimalPlaces: 1,
      ),
      gradientList: gradientList,
      emptyColor: Colors.grey.shade300,
    );
  }
}
