package ceri.m1ilsen.applicationprojetm1.align;

/**
 * Created by Laurent on 04/05/2018.
 */

/**
 * 维特比算法
 * @author hankcs
 */
public class Viterbi
{
    /**
     * 求解HMM模型
     * @param obs Rang d'observation
     * @param states Etat
     * @param start_p Ratio de la période initiale (Condition)
     * @param trans_p Taux de transition Momentum (condition)
     * @param emit_p Profondeur de l'exposition aux rayonnements
     * @return Le rang le plus probable
     */
    public static int[] compute(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emit_p)
    {
        double[][] V = new double[obs.length][states.length];
        int[][] path = new int[states.length][obs.length];

        for (int y : states)
        {
            V[0][y] = start_p[y] * emit_p[y][obs[0]];
            path[y][0] = y;
        }

        for (int t = 1; t < obs.length; ++t)
        {
            int[][] newpath = new int[states.length][obs.length];

            for (int y : states)
            {
                double prob = -1;
                int state;
                for (int y0 : states)
                {
                    double nprob = V[t - 1][y0] * trans_p[y0][y] * emit_p[y][obs[t]];
                    if (nprob > prob)
                    {
                        prob = nprob;
                        state = y0;
                        // Enregistrer le taux maximum
                        V[t][y] = prob;
                        // Diamètre du chemin d'enregistrement
                        System.arraycopy(path[state], 0, newpath[y], 0, t);
                        newpath[y][t] = y;
                    }
                }
            }

            path = newpath;
        }

        double prob = -1;
        int state = 0;
        for (int y : states)
        {
            if (V[obs.length - 1][y] > prob)
            {
                prob = V[obs.length - 1][y];
                state = y;
            }
        }

        return path[state];
    }
}
