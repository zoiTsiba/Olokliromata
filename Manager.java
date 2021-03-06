public class Manager {
	
	private final Parser parser;
	private final SymbolManager symbolManager;
	private final WindowManager windowManager;
	private final ReelManager reelManager;
	private final PaylineManager paylineManager;
	private final PaymentManager paymentManager;
	private final ScatterManager scatterManager;
	private final RewardManager rewardManager;
	
	
	public Manager(String text) {
		if (text == null) {
			throw new IllegalArgumentException("argument to constructor is null");
		}

		this.parser = new Parser(text);
		this.symbolManager = new SymbolManager(this);
		this.windowManager = new WindowManager(this);
		this.reelManager = new ReelManager(this);
		this.paylineManager = new PaylineManager(this);
		this.paymentManager = new PaymentManager(this);
		this.scatterManager = new ScatterManager(this);
		this.rewardManager = new RewardManager(this);
	}
	
	public Parser getParser() {
		return parser;
	}
	
	public SymbolManager getSymbolManager() {
		if (symbolManager == null) {
			throw new NullPointerException("Symbol Manager has not been initialized");
		}
		return symbolManager;
	}
	
	public WindowManager getWindowManager() {
		if (windowManager == null) {
			throw new NullPointerException("Window Manager has not been initialized");
		}
		return windowManager;
	}
	
	public ReelManager getReelManager() {
		if (reelManager == null) {
			throw new NullPointerException("Reel Manager has not been initialized");
		}
		return reelManager;
	}
	
	public PaylineManager getPaylineManager() {
		if (paylineManager == null) {
			throw new NullPointerException("Payline Manager has not been initialized");
		}
		return paylineManager;
	}
	
	public PaymentManager getPaymentManager() {
		if (paymentManager == null) {
			throw new NullPointerException("Payment Manager has not been initialized");
		}
		return paymentManager;
	}
	
	public ScatterManager getScatterManager() {
		if (scatterManager == null) {
			throw new NullPointerException("Scatter Manager has not been initialized");
		}
		return scatterManager;
	}
	
	public RewardManager getRewardManager() {
		if (rewardManager == null) {
			throw new NullPointerException("Reward Manager has not been initialized");
		}
		return rewardManager;
	}

	public static void main(String[] args) {
		
        String filename = "IO/game.txt";
		In in = new In(filename);
		Manager manager = new Manager(in.readAll());
		
		SymbolManager sm = manager.getSymbolManager();
		System.out.println(sm.isWildcard("g"));
		
		ReelManager rm = manager.getReelManager();
		System.out.println(rm);
		
		PaylineManager pm = manager.getPaylineManager();
		System.out.println(pm);
		
		PaymentManager payMgr = manager.getPaymentManager();
		System.out.println(payMgr);
		
		int[] combination = {3,3,3,6}; // {a,a,a,a}
		System.out.println(payMgr.getReward(combination));
		
		
	}

}
