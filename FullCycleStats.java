
public class FullCycleStats {
	private final Manager manager;
	private double FullCycleSize;
	private double RTP;
	private double hitRate;
	private int hits;
	private double totalPrize;
	private double volatility;
	private double stDev;
	StringBuilder sb = new StringBuilder();

	public double getRTP() { return RTP; }

	public double getHitRate() { return hitRate; }

	public int getHits() { return hits; }

	public double getTotalPrize() { return totalPrize; }

	public double getVolatility() { return volatility; }

	public double getStDev() { return stDev; }

	public double getFullCycleSize() { return FullCycleSize; }

	public FullCycleStats(Manager manager) {
		if (manager == null)
			throw new IllegalArgumentException("argument to constructor is null");
		this.manager = manager;
		RTP = 0;
		hitRate = 0;
		hits = 0;
		totalPrize = 0;
		volatility = 0;
		stDev = 0;
		FullCycleSize = 0;
		final PaylineManager paylineManager = manager.getPaylineManager();
		runFullCycle();
	}

	private void runFullCycle() {

		final ReelManager reelManager = manager.getReelManager();
		final SlotMachine slotMachine = reelManager.getSlotMachine();
		final WindowManager windowManager = manager.getWindowManager();
		final PaylineManager paylineManager = manager.getPaylineManager();
		final PaymentManager paymentManager = manager.getPaymentManager();

		// number of reels
		int nReels = slotMachine.getNumberOfReels();

		// to keep track of next element in each of the nReels reels
		int[] indices = new int[nReels];

		while (true) {

			// *****************************************************
			// BEGIN :: do whatever you want with current window
			// *****************************************************
			windowManager.getWindow(indices);
			int scattersum = reelManager.scatterSum(indices);

			String windowStr = "\n" + windowManager.windowToString();
			for (Payline payline : paylineManager.paylines()) {
				int[] paylineSymbols = windowManager.getPaylineSymbols(payline);
				int reward = paymentManager.getReward(paylineSymbols);

				if (reward > 0) {
//                    sb.append(windowStr);
//                    windowStr = "";
//                    sb.append(payline);
//                    sb.append(" pays ");
//                    sb.append(reward);
//                    sb.append("\n");
//                    sb.append("scatters in window: " + scattersum);
//                    System.out.println(sb.toString());
					totalPrize += reward;
					hits++;
				}
				FullCycleSize++;

			}
			// *****************************************************
			// END :: do whatever you want with current window
			// *****************************************************

			// find the rightmost reel that has more
			// elements left after the current element
			// in that reel
			int next = nReels - 1;
			while (next >= 0 && (indices[next] + 1 >= slotMachine.getReel(next).getNumberOfSymbols()))
				next--;

			// no such reel is found so no more combinations left
			if (next < 0)
				return;

			// if found move to next element in that reel
			indices[next]++;

			// for all reels to the right of this
			// reel current index again points to
			// first element
			for (int i = next + 1; i < nReels; i++)
				indices[i] = 0;
		}

	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String filename = "game.txt";
		In in = new In(filename);
		Manager manager = new Manager(in);
		com.company.FullCycleStats fcs = new com.company.FullCycleStats(manager);

		long elapsedTimeMillis = System.currentTimeMillis()-start;
		float elapsedTimeMin = elapsedTimeMillis/(60*1000F);
		fcs.sb.append(" Time :");
		fcs.sb.append(elapsedTimeMin);
		fcs.sb.append("\n");
		fcs.sb.append(" Total prize :");
		fcs.sb.append(fcs.getTotalPrize());
		fcs.sb.append("\n");
		fcs.sb.append(" Hits :");
		fcs.sb.append(fcs.getHits());
		fcs.sb.append("\n");
		fcs.sb.append(" HitRate :");
		fcs.hitRate = fcs.getFullCycleSize() / fcs.getHits();
		fcs.sb.append(fcs.getHitRate());
		fcs.sb.append("\n");
		fcs.sb.append(" RTP :");
		fcs.RTP = fcs.getTotalPrize()/fcs.getFullCycleSize();
		fcs.sb.append( fcs.getRTP());
		fcs.sb.append("\n");
		System.out.println(fcs.sb.toString());
	}

}