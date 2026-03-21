package com.example.simpletarot.data.local

import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.domain.model.TarotCard
import com.example.simpletarot.domain.model.withRankAndSuit
import kotlin.random.Random

private val baseDeck by lazy {
    listOf(
        // Major Arcana
        TarotCard(
            "The Fool",
            "New beginnings, spontaneity, trust",
            "Recklessness, naivety, holding back"
        ),
        TarotCard(
            "The Magician",
            "Manifestation, resourcefulness, power",
            "Manipulation, wasted talent, deception"
        ),
        TarotCard(
            "The High Priestess",
            "Intuition, mystery, inner voice",
            "Secrets, disconnected from intuition, withdrawal"
        ),
        TarotCard(
            "The Empress",
            "Fertility, nurturing, abundance",
            "Dependence, creative block, emptiness"
        ),
        TarotCard(
            "The Emperor",
            "Authority, structure, stability",
            "Tyranny, rigidity, lack of discipline"
        ),
        TarotCard(
            "The Hierophant",
            "Tradition, learning, spiritual guidance",
            "Rebellion, unconventionality, freedom"
        ),
        TarotCard(
            "The Lovers",
            "Partnership, harmony, choices",
            "Imbalance, misalignment, disharmony"
        ),
        TarotCard(
            "The Chariot",
            "Determination, willpower, control",
            "Lack of direction, self-doubt, aggression"
        ),
        TarotCard(
            "Strength",
            "Courage, patience, inner strength",
            "Self-doubt, weakness, insecurity"
        ),
        TarotCard(
            "The Hermit",
            "Introspection, solitude, inner guidance",
            "Isolation, loneliness, withdrawal"
        ),
        TarotCard(
            "Wheel of Fortune",
            "Change, cycles, destiny",
            "Bad luck, resistance to change, setbacks"
        ),
        TarotCard(
            "Justice",
            "Truth, fairness, accountability",
            "Dishonesty, unfairness, corruption"
        ),
        TarotCard(
            "The Hanged Man",
            "Pause, surrender, new perspective",
            "Delays, resistance, stalling"
        ),
        TarotCard(
            "Death",
            "Transformation, endings, renewal",
            "Resistance to change, stagnation, fear"
        ),
        TarotCard(
            "Temperance",
            "Balance, harmony, moderation",
            "Excess, imbalance, conflict"
        ),
        TarotCard(
            "The Devil",
            "Bondage, addiction, materialism",
            "Release, freedom, detachment"
        ),
        TarotCard(
            "The Tower",
            "Sudden change, upheaval, revelation",
            "Avoidance of disaster, fear of change"
        ),
        TarotCard(
            "The Star",
            "Hope, inspiration, renewal",
            "Despair, lack of faith, discouragement"
        ),
        TarotCard(
            "The Moon",
            "Illusion, intuition, dreams",
            "Confusion, fear, misinterpretation"
        ),
        TarotCard(
            "The Sun",
            "Joy, success, vitality",
            "Sadness, pessimism, lack of clarity"
        ),
        TarotCard(
            "Judgement",
            "Awakening, reflection, reckoning",
            "Self-doubt, denial, avoidance"
        ),
        TarotCard(
            "The World",
            "Completion, accomplishment, travel",
            "Lack of closure, delays, incompletion"
        ),

        // Wands
        TarotCard(
            "Ace of Wands",
            "Inspiration, new opportunity, growth",
            "Delays, lack of motivation, false start"
        ),
        TarotCard(
            "Two of Wands",
            "Planning, progress, decisions",
            "Fear of change, playing safe, bad planning"
        ),
        TarotCard(
            "Three of Wands",
            "Expansion, foresight, opportunities",
            "Delays, obstacles, frustration"
        ),
        TarotCard(
            "Four of Wands",
            "Celebration, home, community",
            "Conflict, instability, transition"
        ),
        TarotCard(
            "Five of Wands",
            "Competition, challenge, tension",
            "Avoiding conflict, cooperation, resolution"
        ),
        TarotCard(
            "Six of Wands",
            "Victory, recognition, success",
            "Ego, fall from grace, lack of recognition"
        ),
        TarotCard(
            "Seven of Wands",
            "Perseverance, defense, standing ground",
            "Giving up, overwhelm, vulnerability"
        ),
        TarotCard(
            "Eight of Wands",
            "Speed, movement, swift change",
            "Delays, frustration, resisting change"
        ),
        TarotCard(
            "Nine of Wands",
            "Resilience, persistence, boundaries",
            "Exhaustion, defensiveness, giving up"
        ),
        TarotCard(
            "Ten of Wands",
            "Burden, responsibility, hard work",
            "Burnout, stress, letting go"
        ),
        TarotCard(
            "Page of Wands",
            "Exploration, enthusiasm, discovery",
            "Lack of direction, immaturity, procrastination"
        ),
        TarotCard(
            "Knight of Wands",
            "Energy, passion, adventure",
            "Recklessness, haste, scattered energy"
        ),
        TarotCard(
            "Queen of Wands",
            "Confidence, independence, warmth",
            "Jealousy, insecurity, selfishness"
        ),
        TarotCard(
            "King of Wands",
            "Leadership, vision, honor",
            "Impulsiveness, overbearing, ruthless"
        ),

        // Cups
        TarotCard(
            "Ace of Cups",
            "Love, compassion, new feelings",
            "Blocked emotions, emptiness, coldness"
        ),
        TarotCard(
            "Two of Cups",
            "Connection, partnership, attraction",
            "Breakup, imbalance, tension"
        ),
        TarotCard(
            "Three of Cups",
            "Friendship, joy, celebration",
            "Gossip, overindulgence, isolation"
        ),
        TarotCard(
            "Four of Cups",
            "Apathy, contemplation, reevaluation",
            "Awareness, acceptance, choosing happiness"
        ),
        TarotCard(
            "Five of Cups",
            "Loss, grief, disappointment",
            "Acceptance, moving on, healing"
        ),
        TarotCard(
            "Six of Cups",
            "Nostalgia, innocence, reunion",
            "Stuck in past, naivety, unrealistic"
        ),
        TarotCard(
            "Seven of Cups",
            "Choices, fantasy, illusion",
            "Clarity, decisiveness, realism"
        ),
        TarotCard(
            "Eight of Cups",
            "Walking away, seeking truth",
            "Fear of change, avoidance, stagnation"
        ),
        TarotCard(
            "Nine of Cups",
            "Satisfaction, gratitude, contentment",
            "Greed, dissatisfaction, smugness"
        ),
        TarotCard(
            "Ten of Cups",
            "Harmony, happiness, family",
            "Disconnection, broken relationships, disharmony"
        ),
        TarotCard(
            "Page of Cups",
            "Imagination, sensitivity, curiosity",
            "Emotional immaturity, insecurity, escapism"
        ),
        TarotCard(
            "Knight of Cups",
            "Charm, romance, idealism",
            "Moodiness, disappointment, jealousy"
        ),
        TarotCard(
            "Queen of Cups",
            "Compassion, care, intuition",
            "Codependency, insecurity, smothering"
        ),
        TarotCard(
            "King of Cups",
            "Balance, diplomacy, emotional control",
            "Manipulation, coldness, mood swings"
        ),

        // Swords
        TarotCard(
            "Ace of Swords",
            "Clarity, truth, breakthroughs",
            "Confusion, dishonesty, chaos"
        ),
        TarotCard(
            "Two of Swords",
            "Indecision, stalemate, choices",
            "Lies, confusion, indecision"
        ),
        TarotCard(
            "Three of Swords",
            "Heartbreak, sorrow, betrayal",
            "Recovery, forgiveness, reconciliation"
        ),
        TarotCard(
            "Four of Swords",
            "Rest, recovery, meditation",
            "Restlessness, burnout, stress"
        ),
        TarotCard(
            "Five of Swords",
            "Conflict, defeat, competition",
            "Reconciliation, compromise, harmony"
        ),
        TarotCard(
            "Six of Swords",
            "Transition, moving on, change",
            "Resistance to change, stuck, baggage"
        ),
        TarotCard(
            "Seven of Swords",
            "Deception, stealth, strategy",
            "Confession, clarity, turning over new leaf"
        ),
        TarotCard(
            "Eight of Swords",
            "Restriction, fear, helplessness",
            "Freedom, empowerment, clarity"
        ),
        TarotCard(
            "Nine of Swords",
            "Anxiety, guilt, worry",
            "Hope, comfort, letting go of fear"
        ),
        TarotCard(
            "Ten of Swords",
            "Endings, betrayal, collapse",
            "Recovery, regeneration, resisting an end"
        ),
        TarotCard(
            "Page of Swords",
            "Curiosity, truth-seeking, vigilance",
            "Deception, cynicism, manipulation"
        ),
        TarotCard(
            "Knight of Swords",
            "Ambition, action, drive",
            "Impulsiveness, recklessness, impatience"
        ),
        TarotCard(
            "Queen of Swords",
            "Independence, perception, clarity",
            "Bitterness, coldness, manipulation"
        ),
        TarotCard(
            "King of Swords",
            "Logic, authority, truth",
            "Tyranny, cruelty, manipulation"
        ),

        // Pentacles
        TarotCard(
            "Ace of Pentacles",
            "Opportunity, prosperity, stability",
            "Lost opportunity, scarcity, instability"
        ),
        TarotCard(
            "Two of Pentacles",
            "Balance, adaptability, priorities",
            "Imbalance, overcommitment, disorganization"
        ),
        TarotCard(
            "Three of Pentacles",
            "Teamwork, collaboration, learning",
            "Disharmony, poor teamwork, mediocrity"
        ),
        TarotCard(
            "Four of Pentacles",
            "Security, stability, control",
            "Greed, materialism, insecurity"
        ),
        TarotCard(
            "Five of Pentacles",
            "Poverty, hardship, loss",
            "Recovery, improvement, relief"
        ),
        TarotCard(
            "Six of Pentacles",
            "Generosity, sharing, charity",
            "Debt, selfishness, inequality"
        ),
        TarotCard(
            "Seven of Pentacles",
            "Patience, long-term view, investment",
            "Impatience, waste, lack of reward"
        ),
        TarotCard(
            "Eight of Pentacles",
            "Skill, mastery, hard work",
            "Perfectionism, lack of focus, mediocrity"
        ),
        TarotCard(
            "Nine of Pentacles",
            "Luxury, independence, success",
            "Recklessness, overindulgence, dependence"
        ),
        TarotCard(
            "Ten of Pentacles",
            "Legacy, wealth, stability",
            "Loss, instability, financial failure"
        ),
        TarotCard(
            "Page of Pentacles",
            "Ambition, learning, diligence",
            "Laziness, lack of commitment, foolishness"
        ),
        TarotCard(
            "Knight of Pentacles",
            "Reliability, hard work, responsibility",
            "Stagnation, boredom, laziness"
        ),
        TarotCard(
            "Queen of Pentacles",
            "Nurturing, security, practicality",
            "Selfishness, imbalance, work-home conflict"
        ),
        TarotCard(
            "King of Pentacles",
            "Wealth, discipline, leadership",
            "Greed, stubbornness, exploitation"
        )
    )
}

object TarotDeck {
    fun getDeck(): List<TarotCard> {
        return baseDeck.map { it.withRankAndSuit() }
    }
}
